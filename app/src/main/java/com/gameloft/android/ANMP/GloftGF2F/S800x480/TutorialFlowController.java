package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/** Ephemeral ownership for the original aYR/aYS tutorial state machines. */
final class TutorialFlowController {
    private static long frame;
    private static long lastStepFrame = -1;
    private static long session = 1;
    private static long epoch = 1;
    private static long gesture;
    private static long consumedGesture = -1;
    private static int sequence;
    private static int step = -1;
    private static Modal modal;

    private static final class Modal {
        final int state;
        final long session;
        final long epoch;
        final long openingGesture;
        boolean accepted;

        Modal(int state) {
            this.state = state;
            this.session = TutorialFlowController.session;
            this.epoch = TutorialFlowController.epoch;
            this.openingGesture = gesture;
        }
    }

    private TutorialFlowController() {}

    static synchronized void frameStarted() {
        frame++;
    }

    static synchronized void pointerPressed() {
        gesture++;
    }

    static synchronized void invalidateSession() {
        session++;
        epoch++;
        sequence = 0;
        step = -1;
        lastStepFrame = -1;
        // A release from the previous world cannot acknowledge the new one.
        consumedGesture = gesture;
    }

    private static void observe(int intro, int update, boolean updateActive) {
        int nextSequence = intro > 0 ? 1 : updateActive && update > 0 ? 2 : 0;
        int nextStep = nextSequence == 1 ? intro : nextSequence == 2 ? update : -1;
        if (sequence != nextSequence || step != nextStep) {
            sequence = nextSequence;
            step = nextStep;
            epoch++;
        }
    }

    static synchronized boolean beginStep(int owner, int intro, int update, boolean updateActive) {
        observe(intro, update, updateActive);
        if (owner != sequence || lastStepFrame == frame) return false;
        lastStepFrame = frame;
        return true;
    }

    static synchronized boolean inputConsumed(int intro, int update, boolean updateActive) {
        observe(intro, update, updateActive);
        return sequence != 0 && consumedGesture == gesture;
    }

    static synchronized boolean openModal(int state, int intro, int update, boolean updateActive) {
        observe(intro, update, updateActive);
        if (sequence == 0) {
            modal = null;
            return true;
        }
        if (modal != null && !modal.accepted && current(modal)) return false;
        modal = new Modal(state);
        // The gesture opening a modal belongs to its previous screen.
        consumedGesture = gesture;
        return true;
    }

    private static boolean current(Modal owner) {
        return owner.session == session && owner.epoch == epoch;
    }

    static synchronized void modalActivated(int state, int intro, int update, boolean updateActive) {
        // A request made by an action can precede that action's step advance.
        // Bind to the step actually showing the modal, but never revive a
        // request belonging to a world that was unloaded in the meantime.
        if (modal != null && (modal.state != state || modal.session != session)) return;
        observe(intro, update, updateActive);
        if (sequence == 0) return;
        modal = new Modal(state);
        consumedGesture = gesture;
    }

    static synchronized boolean acceptModal(int state, int intro, int update, boolean updateActive) {
        observe(intro, update, updateActive);
        if (modal == null || modal.state != state) return sequence == 0;
        if (!current(modal) || modal.accepted || gesture <= modal.openingGesture
                || consumedGesture == gesture) return false;
        modal.accepted = true;
        consumedGesture = gesture;
        return true;
    }

    static synchronized boolean closeModal(int state) {
        if (modal == null || modal.state != state) return false;
        boolean consume = !modal.accepted;
        modal.accepted = true;
        if (consume) consumedGesture = gesture;
        modal = null;
        return consume;
    }

    static synchronized boolean ownsModal(int state) {
        return modal != null && modal.state == state;
    }

    static synchronized long captureCallback(int intro, int update, boolean updateActive) {
        observe(intro, update, updateActive);
        return sequence == 0 ? 0 : epoch;
    }

    static synchronized boolean callbackCurrent(long token, int intro, int update, boolean updateActive) {
        observe(intro, update, updateActive);
        return token != 0 && sequence != 0 && token == epoch;
    }

    static synchronized void restoreAfterLoad() {
        if (ci.aZB != 0 || ci.aYR != 18 || ch.als == null || ch.als.length <= 1) return;
        ch grown = null;
        for (ch entity = ch.als[1]; entity != null; entity = entity.aoi) {
            if (entity.Kx != 54) continue;
            // pR advances the original tutorial cow from definition16 to17.
            // A remaining baby still owns the real growth action.
            if (entity.aoC == 16) return;
            if (entity.aoC == 17 && !entity.anw && !entity.fz(32)) {
                if (grown != null) return;
                grown = entity;
            }
        }
        if (grown != null) grown.restoreTutorialGrowthEffect();
    }

    static void restoreWorldUi() {
        if (ci.aZB != 0 || ci.aYR < 10 || ci.aYR > 12) return;
        // jM re-enters the tutorial through Ep, which hides this control.
        // Rebuild only its presentation; persisted quests own their progress.
        ci.e(1, 53, true);
        ci.f(1, 53, true);
        ci.e(1, 65, ci.aYR == 10);
        ci.e(1, 66, ci.aYR == 11);
        ci.Ff();
        ci.gM(1);
    }
}
