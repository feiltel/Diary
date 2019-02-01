package com.nutdiary.diary.network;

import com.nutdiary.diary.bean.UploadBean;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 扩展OkHttp的请求体，实现上传时的进度提示
 */
public class UploadFileRequestBody extends RequestBody {
    public  interface  UploadFileCall{
        void onProgress(int progress);
    }


    private RequestBody mRequestBody;
    private UploadFileCall fileUploadObserver;

    public UploadFileRequestBody(File file, UploadFileCall fileUploadObserver) {
        this.mRequestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        this.fileUploadObserver = fileUploadObserver;
    }


    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
         return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        System.out.println(sink.getClass());
        if (sink instanceof Buffer){
            //因为项目重写了日志拦截器，而日志拦截器里面调用了 RequestBody.writeTo方法，但是 它的sink类型是Buffer类型，所以直接写入
            //如果不这么做的话，上传进度最终会达到200%，因为被调用2次，而且日志拦截的writeTo是直接写入到 buffer 对象中，所以会很快；
            mRequestBody.writeTo(sink);
            return;
        }
        CountingSink countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);
        //写入
        mRequestBody.writeTo(bufferedSink);
        //刷新
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();

    }

    protected final class CountingSink extends ForwardingSink {

        private long bytesWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);

            bytesWritten += byteCount;
            if (fileUploadObserver != null) {
                fileUploadObserver.onProgress((int) (bytesWritten*100 / contentLength()));
            }

        }

    }
}
