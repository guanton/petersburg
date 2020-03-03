import java.sql.SQLOutput;
import java.util.Scanner;

public class simulation {
    int b_games_played;
    int b_max_payout;
    int batch_size;
    int batches;
    int b_total_payout;
    int b_max_run_heads;
    int number_flips;
    int g_num_flips;
    boolean flip_mode;

    public static void main(String[] args) {
        simulation s = new simulation();
        s.get_Request();
        if (s.flip_mode){
            s.run_batches_flip();
        } else {
            s.run_batches_game();
        }
    }

    public void get_Request(){
        Scanner keyboard = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Flip Mode?");
            String input = keyboard.nextLine();
            if(input != null) {
                if ("y".equals(input)) {
                    flip_mode = true;
                    get_Request_Flips();
                    break;
                } else if ("n".equals(input)) {
                    flip_mode = false;
                    get_Request_Games();
                    break;
                }
            }
        }
        keyboard.close();
    }

    public void get_Request_Games(){
        Scanner input = new Scanner(System.in);
        System.out.println("How many batches?");
        batches = input.nextInt();
        Scanner input2 = new Scanner(System.in);
        System.out.println("How many games per batch?");
        batch_size = input2.nextInt();
        resetStats();
    }

    public void get_Request_Flips(){
        Scanner input = new Scanner(System.in);
        System.out.println("How many flips?");
        number_flips = input.nextInt();
        Scanner input2 = new Scanner(System.in);
        System.out.println("How many batches?");
        batches = input2.nextInt();
        resetStats();
    }

    public void resetStats(){
        b_max_payout = 0;
        b_total_payout = 0;
        b_games_played = 0;
        b_max_run_heads = 0;
        g_num_flips = 0;
    }

    public void run_batches_game(){
        for (int b = 1; b <= batches; b++) {
            for (int g=1; g<= batch_size; g++) {
                run_game();
                b_games_played++;
            }
            display_stats_batch();
            resetStats();
        }
    }

    public void run_batches_flip(){
        for (int b = 1; b <= batches; b++) {
            run_flips();
            display_stats_batch();
            resetStats();
        }
    }

    public void run_game(){
        boolean tails = false;
        int heads = 0;
        while (!tails) {
            g_num_flips = g_num_flips + 1;
            if (Math.random() < 0.5){
                tails = true;
            } else {
                heads = heads+1;
            }
        }
        int game_payout = (int) Math.pow(2, heads);
        b_total_payout = b_total_payout + game_payout;
        if (game_payout > b_max_payout) {
            b_max_payout = game_payout ;
            b_max_run_heads = heads;
        }
    }

    public void run_flips(){
        int heads = 0;
        int flips = 0;
        while (flips < number_flips){
            if (Math.random() < 0.5){
                b_games_played = b_games_played + 1;
                if (heads > b_max_run_heads) {
                    b_max_run_heads = heads;
                    b_max_payout = (int) Math.pow(2, heads);
                }
                b_total_payout = b_total_payout + (int) Math.pow(2, heads);
                heads = 0;
            } else {
                heads = heads+1;
            }
            flips = flips + 1;
        }

    }

    public void display_stats_batch(){
        System.out.println("Total Payout: " + b_total_payout);
        System.out.println("Games Played: " + b_games_played);
        System.out.println("Average Payout: " + b_total_payout/b_games_played);
        System.out.println("Max payout: " + b_max_payout);
        System.out.println("Longest Run of Heads: " + b_max_run_heads);
        System.out.println("Log(games played) = " + Math.log(b_games_played)/Math.log(2));
        if (!flip_mode){
            System.out.println("Number of flips: " + g_num_flips);
            System.out.println("Log(flips) = " + Math.log(g_num_flips)/Math.log(2));
        }
        System.out.println("\n");
    }



}
