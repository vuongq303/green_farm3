# Retained SDK startup request: read-only diagnosis

The first-launch log `.local/offline-test-runtime/01-first-launch-pid18372-private.log`
contained a caught `IOException` in `com.gameloft.a.b.e.run(JW_HTTP.java:1187)`:

```text
java.io.IOException: Cleartext HTTP traffic to <configured-backend-host> not permitted
    at com.android.okhttp.HttpHandler$CleartextURLFilter.checkURLPermitted(HttpHandler.java:115)
    at com.android.okhttp.internal.huc.HttpURLConnectionImpl.execute(HttpURLConnectionImpl.java:458)
    at com.android.okhttp.internal.huc.HttpURLConnectionImpl.getResponse(HttpURLConnectionImpl.java:407)
    at com.android.okhttp.internal.huc.HttpURLConnectionImpl.getResponseCode(HttpURLConnectionImpl.java:538)
    at com.gameloft.a.b.e.run(JW_HTTP.java:1187)
    at java.lang.Thread.run(Thread.java:764)
```

The host was redacted here; no identity, password or token is included. This is
a residual **attempt** by retained SDK bootstrap code. The offline manifest
blocks networking; the application source guards do not remove this SDK call.
It must not be described as zero HTTP dispatch or as successful authentication.

Read-only `javap -c -p` inspection of the original conversion established the
ordering precisely:

- `javax.microedition.midlet.MIDlet.akH()` invokes `mj()` at bytecode offset 1;
  the owned `GloftGF2F.mj()` initializes the engine. Only later, at offset 158,
  `akH()` invokes `com.gameloft.android.wrapper.au.Qh()`.
- `au.Qh()` initializes the SDK identity and callback, constructs a
  `Thread(new ay())`, invokes `Thread.start()` at offset 142, then returns. It
  does not join the worker or poll readiness on this caller.
- `ay.run()` invokes Janus `a.Wm()` (datacenter bootstrap). The existing
  bytecode-derived chain and failure behavior are documented in
  [ACCEPT_PROTOCOL_EVIDENCE.md](../../backend/ACCEPT_PROTOCOL_EVIDENCE.md).
- The SDK HTTP worker catches `Exception`, prints the exception, marks the
  request failed (`BT=true`, `BR=false`), records the exception and cancels the
  transport. The observed trace therefore is not an uncaught fatal exception.

The asynchronous bootstrap attempt does not establish a startup wait. In
particular, the game engine was entered before this SDK dispatch. The separate
emulator validation owns the evidence that this particular APK subsequently
reached the farm; the host fixture results do not establish that fact.

Read-only commands used:

```sh
/workspace/tooling/jdk-25/bin/javap -classpath .local/bytecode-tools/greenfarm-original.jar -c -p javax.microedition.midlet.MIDlet
/workspace/tooling/jdk-25/bin/javap -classpath .local/bytecode-tools/greenfarm-original.jar -c -p com.gameloft.android.wrapper.au
```

No SDK patch, source change or APK rebuild was performed for this diagnosis.
