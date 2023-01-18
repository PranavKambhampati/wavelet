import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    ArrayList<String> words = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("All Words: %s", words.toString());
        } else if (url.getPath().equals("/search")) {
            String[] params = url.getQuery().split("=");
            String query;
            if(params[0].equals("s")){
                query = params[1];
                ArrayList<String> result = new ArrayList<>();
                for(String words: words){
                    if(words.contains(query)){
                        result.add(words);
                    }
                }
                return String.format("List of words that contain your search is: %s", result.toString());
            }
            return String.format("Something went wrong!");
        } 
        else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    //num += Integer.parseInt(parameters[1]);
                    words.add(parameters[1]);
                    //return String.format("Number increased by %s! It's now %d", parameters[1], num);
                    return String.format("List of words updated with %s! It now has %s", parameters[1], words.toString());
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
