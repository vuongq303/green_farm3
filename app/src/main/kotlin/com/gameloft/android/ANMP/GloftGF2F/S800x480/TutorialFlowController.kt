package com.gameloft.android.ANMP.GloftGF2F.S800x480

/** Ephemeral ownership for the persisted intro/update tutorial state machines. */
class TutorialFlowController private constructor() {
    private class ModalOwner(
        val engineState: Int,
        val sessionId: Long,
        val stepEpoch: Long,
        val openingGestureId: Long,
        var accepted: Boolean = false
    )

    companion object {
        private var frameIndex = 0L
        private var lastStepFrameIndex = -1L
        private var sessionId = 1L
        private var stepEpoch = 1L
        private var gestureId = 0L
        private var consumedGestureId = -1L
        private var activeSequence = 0
        private var activeStepIndex = -1
        private var modalOwner: ModalOwner? = null

        @JvmStatic fun frameStarted() = synchronized(TutorialFlowController::class.java) {
            frameIndex++
            Unit
        }

        @JvmStatic fun pointerPressed() = synchronized(TutorialFlowController::class.java) {
            gestureId++
            Unit
        }

        @JvmStatic fun invalidateSession() = synchronized(TutorialFlowController::class.java) {
            sessionId++
            stepEpoch++
            activeSequence = 0
            activeStepIndex = -1
            lastStepFrameIndex = -1L
            // A release from the previous world cannot acknowledge the new one.
            consumedGestureId = gestureId
        }

        private fun observe(introStepIndex: Int, updateStepIndex: Int, updateActive: Boolean) {
            val nextSequence = if (introStepIndex > 0) 1 else if (updateActive && updateStepIndex > 0) 2 else 0
            val nextStepIndex = when (nextSequence) {
                1 -> introStepIndex
                2 -> updateStepIndex
                else -> -1
            }
            if (activeSequence != nextSequence || activeStepIndex != nextStepIndex) {
                activeSequence = nextSequence
                activeStepIndex = nextStepIndex
                stepEpoch++
            }
        }

        @JvmStatic fun beginStep(ownerSequence: Int, introStepIndex: Int, updateStepIndex: Int, updateActive: Boolean): Boolean = synchronized(TutorialFlowController::class.java) {
            observe(introStepIndex, updateStepIndex, updateActive)
            if (ownerSequence != activeSequence || lastStepFrameIndex == frameIndex) return@synchronized false
            lastStepFrameIndex = frameIndex
            true
        }

        @JvmStatic fun inputConsumed(introStepIndex: Int, updateStepIndex: Int, updateActive: Boolean): Boolean = synchronized(TutorialFlowController::class.java) {
            observe(introStepIndex, updateStepIndex, updateActive)
            activeSequence != 0 && consumedGestureId == gestureId
        }

        @JvmStatic fun openModal(engineState: Int, introStepIndex: Int, updateStepIndex: Int, updateActive: Boolean): Boolean = synchronized(TutorialFlowController::class.java) {
            observe(introStepIndex, updateStepIndex, updateActive)
            if (activeSequence == 0) {
                modalOwner = null
                return@synchronized true
            }
            val previousOwner = modalOwner
            if (previousOwner != null && !previousOwner.accepted && current(previousOwner)) return@synchronized false
            modalOwner = ModalOwner(engineState, sessionId, stepEpoch, gestureId)
            // The gesture opening a modal belongs to its previous screen.
            consumedGestureId = gestureId
            true
        }

        private fun current(owner: ModalOwner): Boolean = owner.sessionId == sessionId && owner.stepEpoch == stepEpoch

        @JvmStatic fun modalActivated(engineState: Int, introStepIndex: Int, updateStepIndex: Int, updateActive: Boolean) = synchronized(TutorialFlowController::class.java) {
            // An action may request a modal before advancing its step. Bind to
            // the actual displayed step, without reviving an unloaded world.
            val previousOwner = modalOwner
            if (previousOwner != null && (previousOwner.engineState != engineState || previousOwner.sessionId != sessionId)) return@synchronized
            observe(introStepIndex, updateStepIndex, updateActive)
            if (activeSequence == 0) return@synchronized
            modalOwner = ModalOwner(engineState, sessionId, stepEpoch, gestureId)
            consumedGestureId = gestureId
        }

        @JvmStatic fun acceptModal(engineState: Int, introStepIndex: Int, updateStepIndex: Int, updateActive: Boolean): Boolean = synchronized(TutorialFlowController::class.java) {
            observe(introStepIndex, updateStepIndex, updateActive)
            val owner = modalOwner
            if (owner == null || owner.engineState != engineState) return@synchronized activeSequence == 0
            if (!current(owner) || owner.accepted || gestureId <= owner.openingGestureId || consumedGestureId == gestureId) return@synchronized false
            owner.accepted = true
            consumedGestureId = gestureId
            true
        }

        @JvmStatic fun closeModal(engineState: Int): Boolean = synchronized(TutorialFlowController::class.java) {
            val owner = modalOwner
            if (owner == null || owner.engineState != engineState) return@synchronized false
            val consumeGesture = !owner.accepted
            owner.accepted = true
            if (consumeGesture) consumedGestureId = gestureId
            modalOwner = null
            consumeGesture
        }

        @JvmStatic fun ownsModal(engineState: Int): Boolean = synchronized(TutorialFlowController::class.java) {
            modalOwner?.engineState == engineState
        }

        @JvmStatic fun captureCallback(introStepIndex: Int, updateStepIndex: Int, updateActive: Boolean): Long = synchronized(TutorialFlowController::class.java) {
            observe(introStepIndex, updateStepIndex, updateActive)
            if (activeSequence == 0) 0L else stepEpoch
        }

        @JvmStatic fun callbackCurrent(callbackEpoch: Long, introStepIndex: Int, updateStepIndex: Int, updateActive: Boolean): Boolean = synchronized(TutorialFlowController::class.java) {
            observe(introStepIndex, updateStepIndex, updateActive)
            callbackEpoch != 0L && activeSequence != 0 && callbackEpoch == stepEpoch
        }

        @JvmStatic fun restoreAfterLoad() = synchronized(TutorialFlowController::class.java) {
            val entityLists = ch.als
            if (ci.aZB != 0 || ci.aYR != 18 || entityLists == null || entityLists.size <= 1) return@synchronized
            var grownCow: ch? = null
            var entity = entityLists[1]
            while (entity != null) {
                if (entity.Kx == 54) {
                    // The original pR advances the tutorial cow definition16→17.
                    // A remaining calf still owns the real growth action.
                    if (entity.aoC == 16) return@synchronized
                    if (entity.aoC == 17 && !entity.anw && !entity.fz(32)) {
                        if (grownCow != null) return@synchronized
                        grownCow = entity
                    }
                }
                entity = entity.aoi
            }
            grownCow?.restoreTutorialGrowthEffect()
            Unit
        }

        @JvmStatic fun restoreWorldUi() {
            if (ci.aZB != 0 || ci.aYR < 10 || ci.aYR > 12) return
            // Ep hides the control when jM re-enters tutorial. Rebuild its
            // presentation only; persisted quests continue to own progression.
            ci.e(1, 53, true)
            ci.f(1, 53, true)
            ci.e(1, 65, ci.aYR == 10)
            ci.e(1, 66, ci.aYR == 11)
            ci.Ff()
            ci.gM(1)
        }
    }
}
