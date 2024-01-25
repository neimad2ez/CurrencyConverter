# CurrencyConverter

Using Java and the exchangerate-api I have created a Currency Converter that works with over 161 currencies around the world. Currently only works with 10 currencies as it's a work in progress. My code sends HTTP request to request data which is read and converted using an up-to-date conversion rate. The code uses a BufferedReader to read the data from the server and adds it to a StringBuffer. Data retrieved stored as JSON, JSON is read and outputted back into BigDecimal.
