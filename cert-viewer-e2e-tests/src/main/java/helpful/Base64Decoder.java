package helpful;

import java.util.Base64;
import org.json.JSONObject;

public class Base64Decoder {
    public static UserData decode(String encodedString) {
        String decodedString = new String(Base64.getDecoder().decode(encodedString));

        JSONObject json = new JSONObject(decodedString);

        //Review -> ^ w tym miejscu kończy sie dekodowanie

        //Review -> Zrób USerDataFactory, które
        //  na wejściu dostaje JSONObject
        //  przed stworzeniem samego oiektu UserData, robi te uppercasy
        String firstName = json.getString("firstName");
        String lastName = json.getString("lastName");
        String certType = json.getString("certType");
        String certLevel = json.getString("certLevel");

        String[] variables = {firstName, lastName, certType, certLevel};

        //Review -> użyć streamów stream().map().collect
        for (int i = 0; i < variables.length; i++) {
            String variable = variables[i];
            if (!variable.isEmpty()) {
                variables[i] = variable.substring(0, 1).toUpperCase() + variable.substring(1);
            }
        }

        firstName = variables[0];
        lastName = variables[1];
        certType = variables[2];
        certLevel = variables[3];

        UserData userData = new UserData("", "", "", "");

        userData.setFirstName(firstName);
        userData.setLastName(lastName);
        userData.setCertType(certType);
        userData.setCertLevel(certLevel);

        return userData;
    }
}
