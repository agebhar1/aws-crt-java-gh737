# Test Project: StackOverflowError on Alpine when using DefaultS3CrtAsyncClient

Issue: https://github.com/awslabs/aws-crt-java/issues/737

```sh
$ mvn clean package
$ docker compose up -d
```

## Java - glibc

```sh
$ docker compose exec jammy java -jar -Daws.crt.memory.tracing=2 -Daws.crt.log.level=Trace -Daws.crt.log.destination=File -Daws.crt.log.filename=jammy.log /mnt/aws-crt-java-with-dependencies.jar
06:52:57.913 [main] INFO  AlpineStackOverflowError - Creating AWS Resources
```

Tracefile was **not** created.

```sh
$ docker compose exec jammy java -jar -Daws.crt.memory.tracing=2 -Daws.crt.log.level=Trace -Daws.crt.log.destination=Stderr /mnt/aws-crt-java-with-dependencies.jar 2>&1 | tee jammy.log
```

See [jammy.log](jammy.log)

## Java - Alpine

```sh
$ docker compose exec alpine java -jar /mnt/aws-crt-java-with-dependencies.jar 
07:03:00.423 [main] INFO  AlpineStackOverflowError - Creating AWS Resources
07:03:00.573 [main] ERROR AlpineStackOverflowError - Got exception
java.util.concurrent.ExecutionException: software.amazon.awssdk.core.exception.SdkClientException: Unable to execute HTTP request: null
        at java.base/java.util.concurrent.CompletableFuture.reportGet(CompletableFuture.java:395)
        at java.base/java.util.concurrent.CompletableFuture.get(CompletableFuture.java:2028)
        at AlpineStackOverflowError.createAwsResources(AlpineStackOverflowError.java:31)
        at AlpineStackOverflowError.main(AlpineStackOverflowError.java:46)
Caused by: software.amazon.awssdk.core.exception.SdkClientException: Unable to execute HTTP request: null
        at software.amazon.awssdk.core.exception.SdkClientException$BuilderImpl.build(SdkClientException.java:111)
        at software.amazon.awssdk.core.exception.SdkClientException.create(SdkClientException.java:47)
        at software.amazon.awssdk.core.internal.http.pipeline.stages.utils.RetryableStageHelper2.setLastException(RetryableStageHelper2.java:226)
        at software.amazon.awssdk.core.internal.http.pipeline.stages.utils.RetryableStageHelper2.setLastException(RetryableStageHelper2.java:221)
        at software.amazon.awssdk.core.internal.http.pipeline.stages.AsyncRetryableStage2$RetryingExecutor.maybeRetryExecute(AsyncRetryableStage2.java:151)
        at software.amazon.awssdk.core.internal.http.pipeline.stages.AsyncRetryableStage2$RetryingExecutor.lambda$attemptExecute$1(AsyncRetryableStage2.java:113)
        at java.base/java.util.concurrent.CompletableFuture.uniWhenComplete(CompletableFuture.java:859)
        at java.base/java.util.concurrent.CompletableFuture$UniWhenComplete.tryFire(CompletableFuture.java:837)
        at java.base/java.util.concurrent.CompletableFuture.postComplete(CompletableFuture.java:506)
        at java.base/java.util.concurrent.CompletableFuture.completeExceptionally(CompletableFuture.java:2094)
        at software.amazon.awssdk.utils.CompletableFutureUtils.lambda$forwardExceptionTo$0(CompletableFutureUtils.java:78)
        at java.base/java.util.concurrent.CompletableFuture.uniWhenComplete(CompletableFuture.java:859)
        at java.base/java.util.concurrent.CompletableFuture$UniWhenComplete.tryFire(CompletableFuture.java:837)
        at java.base/java.util.concurrent.CompletableFuture.postComplete(CompletableFuture.java:506)
        at java.base/java.util.concurrent.CompletableFuture.completeExceptionally(CompletableFuture.java:2094)
        at software.amazon.awssdk.core.internal.http.pipeline.stages.MakeAsyncHttpRequestStage.lambda$execute$0(MakeAsyncHttpRequestStage.java:108)
        at java.base/java.util.concurrent.CompletableFuture.uniWhenComplete(CompletableFuture.java:859)
        at java.base/java.util.concurrent.CompletableFuture$UniWhenComplete.tryFire(CompletableFuture.java:837)
        at java.base/java.util.concurrent.CompletableFuture.postComplete(CompletableFuture.java:506)
        at java.base/java.util.concurrent.CompletableFuture.completeExceptionally(CompletableFuture.java:2094)
        at software.amazon.awssdk.core.internal.http.pipeline.stages.MakeAsyncHttpRequestStage.completeResponseFuture(MakeAsyncHttpRequestStage.java:255)
        at software.amazon.awssdk.core.internal.http.pipeline.stages.MakeAsyncHttpRequestStage.lambda$executeHttpRequest$3(MakeAsyncHttpRequestStage.java:167)
        at java.base/java.util.concurrent.CompletableFuture.uniHandle(CompletableFuture.java:930)
        at java.base/java.util.concurrent.CompletableFuture$UniHandle.tryFire(CompletableFuture.java:907)
        at java.base/java.util.concurrent.CompletableFuture$Completion.run(CompletableFuture.java:478)
        at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
        at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
        at java.base/java.lang.Thread.run(Thread.java:829)
Caused by: java.lang.StackOverflowError: null
        at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:579)
        at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:178)
        at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:527)
        at java.base/java.lang.ClassLoader.defineClass1(Native Method)
        at java.base/java.lang.ClassLoader.defineClass(ClassLoader.java:1022)
        at java.base/java.security.SecureClassLoader.defineClass(SecureClassLoader.java:174)
        at java.base/jdk.internal.loader.BuiltinClassLoader.defineClass(BuiltinClassLoader.java:800)
        at java.base/jdk.internal.loader.BuiltinClassLoader.findClassOnClassPathOrNull(BuiltinClassLoader.java:698)
        at java.base/jdk.internal.loader.BuiltinClassLoader.loadClassOrNull(BuiltinClassLoader.java:621)
        at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:579)
        at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:178)
        at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:527)
        at java.base/java.lang.ClassLoader.defineClass1(Native Method)
        at java.base/java.lang.ClassLoader.defineClass(ClassLoader.java:1022)
        at java.base/java.security.SecureClassLoader.defineClass(SecureClassLoader.java:174)
        at java.base/jdk.internal.loader.BuiltinClassLoader.defineClass(BuiltinClassLoader.java:800)
        at java.base/jdk.internal.loader.BuiltinClassLoader.findClassOnClassPathOrNull(BuiltinClassLoader.java:698)
        at java.base/jdk.internal.loader.BuiltinClassLoader.loadClassOrNull(BuiltinClassLoader.java:621)
        at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:579)
        at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:178)
        at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:527)
        at software.amazon.awssdk.core.internal.handler.BaseAsyncClientHandler.lambda$new$0(BaseAsyncClientHandler.java:66)
        at software.amazon.awssdk.core.internal.http.async.AsyncResponseHandler.lambda$prepare$0(AsyncResponseHandler.java:92)
        at java.base/java.util.concurrent.CompletableFuture$UniCompose.tryFire(CompletableFuture.java:1072)
        at java.base/java.util.concurrent.CompletableFuture.postComplete(CompletableFuture.java:506)
        at java.base/java.util.concurrent.CompletableFuture.complete(CompletableFuture.java:2079)
        at software.amazon.awssdk.core.internal.http.async.AsyncResponseHandler$BaosSubscriber.onComplete(AsyncResponseHandler.java:135)
        at software.amazon.awssdk.core.internal.metrics.BytesReadTrackingPublisher$BytesReadTracker.onComplete(BytesReadTrackingPublisher.java:74)
        at software.amazon.awssdk.utils.async.SimplePublisher.doProcessQueue(SimplePublisher.java:275)
        at software.amazon.awssdk.utils.async.SimplePublisher.processEventQueue(SimplePublisher.java:224)
        at software.amazon.awssdk.utils.async.SimplePublisher.complete(SimplePublisher.java:157)
        at software.amazon.awssdk.services.s3.internal.crt.S3CrtResponseHandlerAdapter.onSuccessfulResponseComplete(S3CrtResponseHandlerAdapter.java:140)
        at software.amazon.awssdk.services.s3.internal.crt.S3CrtResponseHandlerAdapter.onFinished(S3CrtResponseHandlerAdapter.java:135)
        at software.amazon.awssdk.crt.s3.S3MetaRequestResponseHandlerNativeAdapter.onFinished(S3MetaRequestResponseHandlerNativeAdapter.java:25)
```

```sh
$ docker compose exec alpine java -jar -Daws.crt.memory.tracing=2 -Daws.crt.log.level=Trace -Daws.crt.log.destination=Stderr /mnt/aws-crt-java-with-dependencies.jar 2>&1 | tee alpine.log
```

See [alpine.log](alpine.log)