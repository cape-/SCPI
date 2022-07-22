import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {

  //get body
  def body = message.getBody(java.lang.String);

  //log body
  def messageLog = messageLogFactory.getMessageLog(message);
  messageLog.addAttachmentAsString("SAP ECC Notification response", body, "text/plain");

  return message;
}
