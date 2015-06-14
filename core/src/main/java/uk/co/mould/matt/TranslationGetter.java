package uk.co.mould.matt;


import java.io.IOException;

public final class TranslationGetter {
    public static void main(String[] args) throws IOException {
//        UrlAccessor urlAccessor = new UrlAccessor();
//        VerbListParser verbListParser = new VerbListParser(new InputSource(new FileInputStream("/Users/mouldm02/workspace/frenchverbinator/core/res/verbs-fr.xml")));
//
//        File file = new File("/Users/mouldm02/Desktop/translations.csv");
//        PrintWriter out = new PrintWriter(file);
//
//        int count = 0;;
//        for (InfinitiveVerb infinitiveVerb : verbListParser.getVerbs()) {
//            count++;
//            String uri = String.format("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20150608T112803Z.af9fff6718eb6de6.c2578af47582da0c1ecabc3fd48bd3af15954bbe&lang=fr-en&text=%s", URIUtil.encode(infinitiveVerb.toString()));
//            String response = null;//urlAccessor.getResponse(new URL(uri));
//            JSONObject obj = new JSONObject(response);
//            JSONArray translation = obj.getJSONArray("text");
//            String french = infinitiveVerb.toString();
//            String english = translation.getString(0);
//
//            String outputLine = String.format("%s,%s", french, "To " + english);
//            out.println(outputLine);
//            System.out.println(String.format("%d of %d", count, verbListParser.getVerbs().size()));
//        }
//        out.close();


//    }
//
//    private static class UrlAccessor {
//        public String getResponse(URL url) throws IOException {
//            String result = "";
//            String line;
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            while ((line = rd.readLine()) != null) {
//                result += line;
//            }
//            rd.close();
//            return result;
//        }
//
    }
}
