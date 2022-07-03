import com.sap.it.api.ITApi;
import com.sap.it.api.ITApiFactory;
import com.sap.it.api.securestore.*;
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

def Message processData(Message message) {

  String ADOBESIGN_BEARER_TOKEN;
  def service = ITApiFactory.getApi(SecureStoreService.class, null);

  // retrieve secure parameter object
  def credential = service.getUserCredential("ADOBESIGN_BEARER_TOKEN");
  if (credential == null) {
    throw new IllegalStateException("No credential found for alias 'ADOBESIGN_BEARER_TOKEN'");
  } else {
    // get secure parameter value
    ADOBESIGN_BEARER_TOKEN = new String(credential.getPassword());
  }

  // store secure parameter value in property which can be used in later stage of your integration process.
  message.setProperty("ADOBESIGN_BEARER_TOKEN", ADOBESIGN_BEARER_TOKEN);

  return message;
}
