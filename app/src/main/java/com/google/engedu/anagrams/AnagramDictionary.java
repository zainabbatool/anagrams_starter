/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList <String> wordList = new ArrayList<String>();
    private HashSet <String> wordSet = new HashSet<String>();
    private HashMap<String,ArrayList> lettersToWord=new HashMap<String,ArrayList>();
    private HashMap<Integer,ArrayList> sizeToWords=new HashMap<Integer,ArrayList>();
    private static int wordLength = DEFAULT_WORD_LENGTH;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);

            /*if(sizeToWords.containsKey(word.length())){
                sizeToWords.get(word.length()).add(word);
            }
            else{
                ArrayList <String> wordL = new ArrayList<String>();
                ArrayList <String> temporary = new ArrayList<String>();
                temporary.add(word);
                wordL.addAll(temporary);
                sizeToWords.put(word.length(),wordL);
                Log.d("Zainab", wordL.toString());
                temporary.clear();

            }*/

            if(lettersToWord.containsKey(sortLetters(word))){
                lettersToWord.get(sortLetters(word)).add(word);
            }
            else{
                ArrayList <String> wordL = new ArrayList<String>();
                ArrayList <String> temporary = new ArrayList<String>();
                temporary.add(word);
                wordL.addAll(temporary);
               lettersToWord.put(sortLetters(word),wordL);
               Log.d("Zainab", wordL.toString());
               temporary.clear();

            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word)&& word.contains(base)==false) {
            return true;
        }
        return false;
    }

    public List<String> getAnagrams(String targetWord) {
        String alphaInput= sortLetters(targetWord);
        ArrayList<String> result = lettersToWord.get(alphaInput);
        /*for(String i:wordList){
            if(i.length()==alphaInput.length()) {
                if (sortLetters(i).equals(alphaInput)) {
                    result.add(i);
                }
            }
            Log.d("Zainab", anagrams.toString());
        }*/
        return result;
    }

    public String sortLetters(String checkWord){
        char[] chars= checkWord.toCharArray();
        Arrays.sort(chars);
        String sorted=new String(chars);
        return sorted;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        //char[] alphabet= "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for(char i='a'; i<='z';i++){
            String newWord = word + i;
            if(lettersToWord.get(sortLetters(newWord))!=null) {
                result.addAll(lettersToWord.get(sortLetters(newWord)));
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        //Collections.shuffle(wordList);
        //wordList.get(0);
        Random randomno= new Random();
        boolean wrapping=false;
        int startingPoint=randomno.nextInt(wordList.size());
        while(wrapping==false){
            for (int arrayIndex=startingPoint;arrayIndex<wordList.size();arrayIndex++){

                if(lettersToWord.get(sortLetters(wordList.get(arrayIndex))).size()>=MIN_NUM_ANAGRAMS){
                    wrapping=true;
                    return wordList.get(arrayIndex);
                }
                else{
                    //new if loop to check if null string then loops to start
                    wrapping=false;
                }

            }
            startingPoint=0;
        }

        return null;
    }
}
