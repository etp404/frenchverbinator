import java.io.IOException;

public final class VerbFileSplitterAndTranslator {
    public static void main(String[] args) throws IOException {
//
//        Map<String, String> verbAndTranslationMap = new HashMap<>();
//        File file = new File("/Users/mouldm02/Desktop/translations.csv");
//        FileReader fileReader = new FileReader(file);
//        BufferedReader bufferedInputStream = new BufferedReader(fileReader);
//        String line;
//        int countSame = 0;
//        int countDiff = 0;
//        while ((line = bufferedInputStream.readLine()) != null) {
//            String[] verbAndTranslation = line.split(",");
//            if (("To " + verbAndTranslation[0]).equals(verbAndTranslation[1])) {
//                countSame++;
//            }
//            else {
//                countDiff++;
//            }
//            verbAndTranslationMap.put(verbAndTranslation[0], verbAndTranslation[1]);
//        }
//
//        List<String> verbList = new ArrayList<>(verbAndTranslationMap.keySet());
//        Collections.sort(verbList);
//
//
//        File fileOutputUntranslated = new File("/Users/mouldm02/Desktop/translation3/sorted_untranslated.csv");
//        File fileOutputTranslated = new File("/Users/mouldm02/Desktop/translation3/sorted_translated.csv");
//        PrintWriter outUntranslated = new PrintWriter(fileOutputUntranslated);
//        PrintWriter outTranslated = new PrintWriter(fileOutputTranslated);
//        int from = 0;
//        int to = 0;
//        int count = 0;
//        while(to<verbList.size()) {
//            from = to;
//            to = to + 500;
//            if (to > verbList.size()) {
//                to = verbList.size();
//            }
//            count++;
//            List<String> verbSubList = verbList.subList(from, to);
//            File fileOutput = new File("/Users/mouldm02/Desktop/translation3/sorted_translations_" + count + ".csv");
//            PrintWriter out = new PrintWriter(fileOutput);
//            for (String verb : verbSubList) {
//                String outputLine = String.format("%s,%s", verb, verbAndTranslationMap.get(verb));
//                if (("To " + verb).equals(verbAndTranslationMap.get(verb))) {
//                    outUntranslated.println(outputLine);
//                }
//                else {
//                    outTranslated.println(outputLine);
//                    out.println(outputLine);
//                }
//                System.out.println(outputLine);
//            }
//            out.close();
//
//
//
//        }
//        outUntranslated.close();
    }
}
