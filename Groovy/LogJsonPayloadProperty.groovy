import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.JsonSlurper;
def Message processData(Message message) {
    //Body     
    def body = message.getBody(java.lang.String);
    def payload = new JsonSlurper().parseText(body);
    def messageLog = messageLogFactory.getMessageLog(message);

    if ( payload.AccessToken != null && payload.AccessToken != "" ){
        messageLog.addAttachmentAsString("Santander AUTH Access Token", payload.AccessToken.substring(0,15) + "...", "text/plain");
    }
    return message;    
}
