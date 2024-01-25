import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import org.json.JSONObject;

public class CurrencyConverter {

  public static void main(String[] args) throws IOException {
    HashMap<Integer, String> currencyCodes = new HashMap<>();

    // Add currency codes
    currencyCodes.put(1, "GBP");
    currencyCodes.put(2, "USD");
    currencyCodes.put(3, "EUR");
    currencyCodes.put(4, "CAD");
    currencyCodes.put(5, "INR");
    currencyCodes.put(6, "SEK");
    currencyCodes.put(7, "RUB");
    currencyCodes.put(8, "CNY");
    currencyCodes.put(9, "JPY");
    currencyCodes.put(10, "AUD");


    int from;
    int to;
    String fromCode;
    String toCode;
    BigDecimal amount;

    Scanner scanner = new Scanner(System.in);

    System.out.println("Welcome to the currency converter!");

    System.out.println("What currency are we converting from?");

    System.out.println(
        "1:GBP (Great British Pound) \n2:USD (US Dollar) \n3:EUR (Euro) \n4:CAD (Canadian Dollar) \n5:INR (Indian Rupee)\n6:SEK (Swedish Krona)\n7:RUB (Russian Ruble)\n8:CNY (Chinese Renminbi)\n9:JPY (Japanese Yen)\n10:AUD (Australian Dollar)");
    from = Integer.valueOf(scanner.nextLine());
    while (from < 1 || from > 10) {
      System.out.println("Please select a valid currency (1-10)");
      System.out.println(
          "1:GBP (Great British Pound) \n2:USD (US Dollar) \n3:EUR (Euro) \n4:CAD (Canadian Dollar) \n5:INR (Indian Rupee)");
      from = Integer.valueOf(scanner.nextLine());
    }
    fromCode = currencyCodes.get(from); //Gets currency code from HashMap

    System.out.println("What currency are we converting to?");
    System.out.println(
        "1:GBP (Great British Pound) \n2:USD (US Dollar) \n3:EUR (Euro) \n4:CAD (Canadian Dollar) \n5:INR (Indian Rupee)");
    to = Integer.valueOf(scanner.nextLine());
    while (to < 1 || to > 10) {
      System.out.println("Please select a valid currency (1-10)");
      System.out.println(
          "1:GBP (Great British Pound) \n2:USD (US Dollar) \n3:EUR (Euro) \n4:CAD (Canadian Dollar) \n5:INR (Indian Rupee)\n6:SEK (Swedish Krona)\n7:RUB (Russian Ruble)\n8:CNY (Chinese Renminbi)\n9:JPY (Japanese Yen)\n10:AUD (Australian Dollar)");
      to = Integer.valueOf(scanner.nextLine());
    }
    toCode = currencyCodes.get(to);

    System.out.println("Amount you wish to convert?");
    amount = scanner.nextBigDecimal(); //Amount to be converted

    sendHTTPGETRequest(fromCode, toCode, amount); //Calls method
  }

  private static void sendHTTPGETRequest(String fromCode, String toCode, BigDecimal amount)
      throws IOException {

    String GET_URL =
        "https://v6.exchangerate-api.com/v6/b802b01cc3b690b08b9b7066/pair/" + fromCode + "/"
            + toCode;
    URL url = new URL(GET_URL); //Sets GET_URL as a proper URL
    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //Makes proper HTTP get request
    httpURLConnection.setRequestMethod(
        "GET"); //Sets request method as get to retrieve something from server
    int responseCode = httpURLConnection.getResponseCode(); //Response the server gives back to us

    if (responseCode == HttpURLConnection.HTTP_OK) { // Success
      BufferedReader in = new BufferedReader(new InputStreamReader(
          httpURLConnection.getInputStream())); //Use buffered reader to read data from server
      String inputLine;
      StringBuffer response = new StringBuffer();

      while ((inputLine = in.readLine())
          != null) { //While there are lines to read, it continues reading until there are none
        response.append(inputLine); //Add each line to stringBuffer
      }
      in.close();

      JSONObject obj = new JSONObject(response.toString()); //Creates new JSON object
      BigDecimal conversionRate = obj.getBigDecimal(
          "conversion_rate"); //Retreives data from "conversion_rate"
      System.out.println(
          "Conversion rate from " + fromCode + " to " + toCode + ": " + conversionRate);
      BigDecimal converted = amount.multiply(conversionRate);
      System.out.println(
          amount + " " + fromCode + " to " + toCode + " is " + converted + " " + toCode);
    } else {
      System.out.println("GET Request Failed!"); //If anything goes wrong it outputs error statement
    }

  }
}