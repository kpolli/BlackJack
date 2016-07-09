package src.BlackJack;

import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution {
  private static String shape = " ";
  
  public static int dishCard(Map<String, ArrayList<Integer>> stackCards) {
	  Random rd = new Random();
	  int s = rd.nextInt(4) + 1;
	  switch (s){
	  case 1:
		  shape = "Spades";
		  break;
	  case 2:
		  shape = "Love";
		  break;
	  case 3:
		  shape = "Clubs";
		  break;
	  case 4:
		  shape = "Diamonds";
		  break;
	  default:
			  break;
	  }
	  ArrayList<Integer> cards = stackCards.get(shape);
	  
	  int r = rd.nextInt(13) + 1;
	  // Ensure a number is not generated more than 4 times
	  int occur = Collections.frequency(cards, r);
	  if (occur < 1) {
		  cards.add(r);
		  stackCards.put(shape, cards);
		  return r;
	  } else {
		  r = dishCard(stackCards);
	  }
	  	return r;
  }
 
  
  public static int playCard(ArrayList<Integer> cards) {
	  int total = 0;
	  // Reverse the order of cards so A is calculated last
	  // in order to determine if to set A = 1 or 11
	  Collections.sort(cards, Collections.reverseOrder());

	  for (int i = 0; i < cards.size(); i++){
	      if (cards.get(i) < 11 && cards.get(i) > 1) {
	        total = total + cards.get(i);
	      } else if (cards.get(i) > 10) {
	        total = total + 10;	
	      } else if (cards.get(i) == 1){
	        if (total < 11) {
	          total = total + 11;
	        } else {
	          total = total + 1;
	        }
	      }
	  }
    return total;
  }
  
  private static String convertCard(int c) {
	  String card = " ";
	  if (c > 1 && c < 11) {
		  card = Integer.toString(c);
	  } else if (c == 11) {
		  card = "J";
	  } else if (c == 12) {
		  card = "Q";
	  } else if (c == 13) {
		  card = "K";
	  } else if (c == 1) {
		  card = "A";
	  }
	  return card;
  }
  
  private static int playerTurn(ArrayList<Integer> arr, String name, Map<String, ArrayList<Integer>> stackCards) {
	  
      int play = dishCard(stackCards);
      arr.add(play);
      int total = playCard(arr);
      
      System.out.println(name + " receives card of shape " + shape + " and value "+ convertCard(play));
      return total;
  }
  
  public static void main(String[] args) throws IOException {
    BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("Enter name player one: ");
    String user1 = buff.readLine();
    System.out.println("Enter name player two: ");
    String user2 = buff.readLine();
    
    System.out.println("Welcome " + user1);
    System.out.println(" ");
    System.out.println(" ");
    System.out.println(" ");
    System.out.println(" ");
    System.out.println("Welcome " + user2);
    
    System.out.println("Press 1 to start game, press any other number to end it: ");
    String decision = buff.readLine();
    String dec1 = " ", dec2 = " ";
    
    
    if (decision.equals("1")) {
      Map<String, ArrayList<Integer>> stackCards = new HashMap<String, ArrayList<Integer>>();
      stackCards.put("Spades", new ArrayList<Integer>());
      stackCards.put("Love", new ArrayList<Integer>());
      stackCards.put("Clubs", new ArrayList<Integer>());
      stackCards.put("Diamonds", new ArrayList<Integer>());
      
      ArrayList<Integer> arr1 = new ArrayList<Integer>();
      ArrayList<Integer> arr2 = new ArrayList<Integer>();
      int total1 = 0, total2 = 0;
      
      total1 = playerTurn(arr1, user1, stackCards);
      total2 = playerTurn(arr2, user2, stackCards);
      
      total1 = playerTurn(arr1, user1, stackCards);
      total2 = playerTurn(arr2, user2, stackCards);
      
      boolean gameover = false;
      
      while (!gameover) {
    	System.out.println("Round ended");
        boolean busted1 = false;
        boolean busted2 = false;
        if (total1 < 21 && !busted1 && !dec1.equals("s")) {
          System.out.println(user1 + " , press h to hit or s to stay");
          dec1 = buff.readLine();
        }else if  (dec1.equals("s")) {
        	System.out.println(user1 + " is waiting for game to finish!");
        } else if (total1 == 21) {
        	System.out.println(user1 + " has perfect score!");
        	busted1 = true;
        } else if (total1 > 21){
          System.out.println(user1 + " sorry you're busted");
          busted1 = true;
        }
        if (total2 < 21 && !busted2 && !dec2.equals("s")) {

          System.out.println(user2 + " , press h to hit or s to stay");
          dec2 = buff.readLine();
        } else if  (dec2.equals("s")) {
        	System.out.println(user2 + " is waiting for game to finish!");
        } else if (total2 == 21) {
        	System.out.println(user2 + " has perfect score!");
        	busted2 = true;
        } else if (total2 > 21){
          System.out.println(user2 + " sorry you're busted");
          busted2 = true;
        }
        if ((dec1.equals("s") && dec2.equals("s")) || (busted1 || busted2)) {
          gameover = true;
        } else if (dec1.equals("h") && dec2.equals("h")) {
        	total1 = playerTurn(arr1, user1, stackCards);         
        	total2 = playerTurn(arr2, user2, stackCards);
        	
        } else if(dec1.equals("h")) {
        	total1 = playerTurn(arr1, user1, stackCards);

        } else if(dec2.equals("h")) {
        	total2 = playerTurn(arr2, user2, stackCards);

        } else {
          System.out.println("Invalid key entered, please try again");
        }        
      }
      
      if (total1 == total2)
      {
        System.out.println("It's a tie as both players have the same count");
      } else if (total1 > 21 && total2 > 21) {
        System.out.println("Both users lost");
      } else if (total1 > 21) {
        System.out.println(user2 + " wins!!!");
      } else if (total2 > 21) {
        System.out.println(user1 + " wins!!!");
    } else {
        int total = Math.max(total1, total2);
        if (total == total1) {
          System.out.println(user1 + " wins!!!");
          
        } else {
          System.out.println(user2 + " wins!!!");
        }
          
      }
    }
    System.out.println("Come and play next time");
  }
}