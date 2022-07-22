import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {

  //get property
  def fnImportQueryString = message.getProperty("fnImportQueryString");

  //log property
  def messageLog = messageLogFactory.getMessageLog(message);
  messageLog.addAttachmentAsString("SAP ECC Notification request", fnImportQueryString, "text/plain");

  return message;
}
