import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {

  //gets exception
  def properties = message.getProperties();
  def exception = properties.get("CamelExceptionCaught");
  def exceptionMessage = getExceptionMessage(exception);

  //logs exception
  def messageLog = messageLogFactory.getMessageLog(message);
  messageLog.addAttachmentAsString("Exception", exceptionMessage, "text/plain");

  //sets body
  message.setBody(exceptionMessage);

  //sets response code
  message.setHeader("CamelHttpResponseCode", 400);

  return message;
}

def String getExceptionMessage(def exception) {
  //checks if exception is empty
  if (exception == null) {
    return "";
  }

  //checks if exception has a cause
  try {
    def cause = exception.getCause();
    if (cause) {
      return getExceptionMessage(cause);
    }
  } catch (ex) {}

  //checks if exception has response body
  try {
    return exception.getResponseBody();
  } catch (ex) {}

  //checks if exception has message
  try {
    return exception.getMessage();
  } catch (ex) {}

  return exception.toString();
}
