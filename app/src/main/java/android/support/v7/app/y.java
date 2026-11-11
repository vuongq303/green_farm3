package android.support.v7.app;

import android.content.res.Resources;

/* JADX INFO: compiled from: AppCompatDelegateImplBase.java */
/* JADX INFO: loaded from: classes.dex */
final class y implements Thread.UncaughtExceptionHandler {
    final /* synthetic */ Thread.UncaughtExceptionHandler ld;

    y(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.ld = uncaughtExceptionHandler;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        if (a(th)) {
            Resources.NotFoundException notFoundException = new Resources.NotFoundException(th.getMessage() + ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.");
            notFoundException.initCause(th.getCause());
            notFoundException.setStackTrace(th.getStackTrace());
            this.ld.uncaughtException(thread, notFoundException);
            return;
        }
        this.ld.uncaughtException(thread, th);
    }

    private boolean a(Throwable th) {
        String message;
        if (!(th instanceof Resources.NotFoundException) || (message = th.getMessage()) == null) {
            return false;
        }
        return message.contains("drawable") || message.contains("Drawable");
    }
}
