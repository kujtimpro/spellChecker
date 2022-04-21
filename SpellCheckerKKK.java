package spellcheckerkkk;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpellCheckerKKK {

    public static boolean spellCheck(String input, String[] dic){
        boolean noErrors = true;
        if(!grammerCheck(input, input.length()))
        {
            noErrors = false;
        }
        if(input.charAt(input.length() - 1)=='.'){
            input = input.substring(0, input.length() -1);
        }
        String[] splitted_Input = input.split("\\s+");
        for(String currentCheck : splitted_Input){
            if(!isSpecial(currentCheck)){
                if(!checkWord(currentCheck,dic)){
                    System.out.println(currentCheck + " is spelt incorrectly");
                    String result = "Did you mean:\n";
                    for(int i=0;i<currentCheck.length()-1;i++){
                        String swaped_Chars = swapChars(currentCheck,i,i+1);
                        if(checkWord(swaped_Chars,dic)){
                            result = result + swaped_Chars + "\n";
                        }
                    }
                    if(!result.equals("Did you mean:\n")){
                        System.out.println(result);
                    }
                    noErrors = false;
                }
            }
        }

        return noErrors;
    }

    public static boolean isSpecial(String input){
        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher match = pattern.matcher(input);
        return match.find();
    }

    public static boolean checkWord(String input, String[] dic){
        boolean valid = false;
        int length = dic.length;
        int i = 0;
        while(!valid && i < length){


            if(input.trim().equalsIgnoreCase(dic[i].trim())){
                valid = true;
                if(input.trim().equals("I")){
                    valid= true;
                }
                else if(input.trim().equals("i")){
                    valid = false;
                }
            }
            i++;

        }
        return valid;
    }

    public static boolean grammerCheck(String input, int length){

        boolean validGrammar =  true;
        int lastCharacter = length - 1;
        if (input.charAt(lastCharacter)!='.'){
            System.out.println("Missing full stop at the end of the sentences");
            validGrammar= false;

        }
        if(!Character.isUpperCase(input.charAt(0)))
        {
            System.out.println("Must starts with an uppercase character or number");

        }
        return validGrammar;
    }
    public static String[] readDictionary(String filepath){

        ArrayList<String> records = new ArrayList<>();
         try
         {
             Scanner scan;
             scan =  new Scanner (new File(filepath));
             scan.useDelimiter("[,\n]");

             while(scan.hasNext())
             {
                 records.add(scan.next());
             }
         }
         catch(Exception e)
         {
             System.out.println(e);
         }
         String[] recordsArray = new String[records.size()];
         recordsArray =  records.toArray(recordsArray);
         return recordsArray;
    }

    private static String swapChars(String str, int lIdx, int rIdx){
        StringBuilder sb = new StringBuilder(str);
        char l = sb.charAt(lIdx), r = sb.charAt(rIdx);
        sb.setCharAt(lIdx, r);
        sb.setCharAt(rIdx, l);
        return sb.toString();
    }


    public static void main(String[] args){

        String[] wordList =  readDictionary("C:\\Users\\MSI\\Documents\\NetBeansProjects\\SpellCheckerKKK\\src\\spellcheckerkkk\\wordlist.txt");
//        String input = "bgaron yes";
        String input = new Scanner(System.in).nextLine();
        if(spellCheck(input,wordList)){
            System.out.println("No errors");
        } else {
            System.out.print("Errors");
        }
    }

}
