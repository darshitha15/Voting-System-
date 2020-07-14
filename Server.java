import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.io.*;

public class Server implements Hello 
{
    
    public Server() {}
    static String st;
    
    List<Voter> voters=new ArrayList<Voter>();
    static ArrayList<String> al=new ArrayList<String>();
    static ArrayList<String> candidatesList=new ArrayList<String>();
    List<String> voted=new ArrayList<String>();;
    Map<String,String> votes=new HashMap<String,String>();
    Map<String,Integer> elecResults=new HashMap<String,Integer>();
    
    public static void Reader(){
        File file = new File("candidates.txt");
        BufferedReader br;
        try {
        br = new BufferedReader(new FileReader(file));
        while ((st = br.readLine()) != null) {
            candidatesList.add(st);
              }
            } catch (IOException e) {
                e.printStackTrace();
            } 
    }  //end of Void reader method
    
    
    public static void Generate(){
        int i;
        char c;
        char d;
        String r;
        for(int x=0;x<1001;x++){
        Random rnd = new Random();
        c = (char) (rnd.nextInt(26) + 'A');
        d = (char) (rnd.nextInt(26) + 'A');
        i = 1234 + rnd.nextInt(9000);
        r =Character.toString(c)+Character.toString(d)+Integer.toString(i);
        al.add(r);
        }
    }  //end of generate method

    public String registerVoter(String s){
        Voter v=new Voter();
        v.id=al.get(0);
        v.name=s;
        voters.add(v);
        al.remove(0);
        return v.id;

    } //end of registerVote method
            

    public String verifyVoter(String s){
        st="Not a valid voter";
        for(Voter v:voters){
            if(v.id.equalsIgnoreCase(s)){
                 st="The voter ID "+s+" is registered with the name "+v.name;
            }
        }
       return st; 
    }   //end of verifyVote method

    public String vote(String candidateName,String voterId){
        if(!candidatesList.contains(candidateName)){
            return "Not a valid candidate";
        }
        if(voted.contains(voterId)){
            return "Already voted";
        }
        st="Invalid";
        for(Voter v:voters){
            if(v.id.equalsIgnoreCase(voterId)){
                st="Valid";
            }
        }

        if(st.equalsIgnoreCase("Invalid"))
            return "Invalid voter";

        votes.put(voterId,candidateName);
        voted.add(voterId);
        return "Successfully voted";
    }  //end of vote method

    public Map<String,Integer> tallyResults(){
        for(String c:candidatesList){
            elecResults.put(c,Collections.frequency(votes.values(), c));
        }
       return elecResults; 
    }   //end of tallyResults method

    public String winner(){
     return elecResults.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
    }

    public static void main(String args[]) {
        
        try {
            Server s = new Server();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(s, 0);

            // Binding the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);

            System.err.println("Server ready");
            Generate();
            Reader();
          } 
            catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }  //end of main method

 } //end of class
