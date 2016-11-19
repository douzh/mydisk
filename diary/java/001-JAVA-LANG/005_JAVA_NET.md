# java.net



# apache httpclient

## HttpConnection

### Socket连接超时的实现

放到一thread中，thread join(timeout)。
线程死了，socket自然也没了。

# apache fileupload

判断是不是含有文件上传的表单提交。

ServletFileUpload.isMultipartContent(request)

实现：

request.getContentType().toLowerCase().startsWith("multipart/")

原理：

上传文件时

Content-Type	multipart/form-data; boundary=......
