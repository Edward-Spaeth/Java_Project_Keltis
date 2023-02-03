package com.KeltisT.Game;

import com.KeltisT.Chips.PhysicalChip;
import com.KeltisT.Controllers.soundController;
import com.KeltisT.Players.Player;
import com.KeltisT.Players.PlayerConfig;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.security.Key;
import java.util.ArrayList;

public class GameEngine {
    private ArrayList<Player> players;
    private Player curr_player;
    private GameBoard gameboard;
    private GameTimer timer;
    private Button takeButton;
    private Button leaveButton;
    private soundController sound;
    private PhysicalChip current_pchip;

    public GameEngine(int amount_of_players, Text timerText, Button takeButton_input, Button leaveButton_input, ArrayList<Label> player_point_labels, AnchorPane blocker, ArrayList<ImageView> current_player_borders){
        players = new ArrayList<>();
        gameboard = new GameBoard(blocker);
        ArrayList<String> player_names = PlayerConfig.get_player_config(amount_of_players);
        for (int i = 0; i < amount_of_players; i++){
            players.add(new Player(player_names.get(i), i, player_point_labels.get(i), current_player_borders.get(i), gameboard.get_dummychips()));
        }
        curr_player = players.get(0);
        curr_player.current_player_border_set_visible(true);
        timer = new GameTimer(timerText, this);
        timer.timer();
        takeButton = takeButton_input;
        leaveButton = leaveButton_input;
        sound = new soundController();
    }
    public void next_turn(Boolean clover_was_played){
        if (!clover_was_played) {
            curr_player = players.get((curr_player.get_order() + 1) % players.size());
        }
        timer.refresh();

        for (PhysicalChip pc : get_gameboard().get_chips()) {
            pc.getPhysical_Chip().setDisable(false);
        }
        for (PhysicalChip pc : get_gameboard().get_chips()) {
            if (!pc.get_is_hidden() && !get_curr_player().get_stacks().get(pc.get_color()).check_if_insert_possible(pc)) {
                pc.getPhysical_Chip().setDisable(true);
            }
        }
        for (Player p : players) {
            p.current_player_border_set_visible(false);
        }
        curr_player.current_player_border_set_visible(true);
    }

    public Boolean check_if_game_over(){
        // Does not work yet...
        Boolean covered_chip_found = Boolean.FALSE;
        for (PhysicalChip pchip : gameboard.get_chips()) {
            if (pchip.get_is_hidden()) {
                covered_chip_found = Boolean.TRUE;
            }
        }
        return !covered_chip_found;
    }

    public ArrayList<Player> determine_winner(){

        ArrayList<Player> players_to_rank = (ArrayList<Player>) players.clone();
        int position_to_award = 1;
        while (players_to_rank.size() > 0) {
            int max_value = players_to_rank.get(0).get_points();
            for (Player p : players_to_rank) {
                if (p.get_points() > max_value) {
                    max_value = p.get_points();
                }
            }
            int amount_players_in_this_rank = 0;
            ArrayList<Player> players_not_in_this_rank = (ArrayList<Player>) players_to_rank.clone();
            for (int i = 0; i < players_to_rank.size(); i++) {
                if (players_to_rank.get(i).get_points() == max_value) {
                    players_to_rank.get(i).set_rank(position_to_award);
                    players_not_in_this_rank.remove(players_to_rank.get(i));
                    amount_players_in_this_rank++;
                }
            }
            players_to_rank = players_not_in_this_rank;
            position_to_award += amount_players_in_this_rank;
        }
        ArrayList<Player> players_in_order = new ArrayList<>();
        for (int position = 1; position <= 4; position++) {
            for (Player p : players) {
                if (p.get_rank() == position) {
                    players_in_order.add(p);
                }
            }
        }
        System.out.println("DETERMINE WINNERS");
        for (Player p : players_in_order) {
            System.out.println("Rank: " + p.get_rank() + ", score: " + p.get_points());
        }
        return players_in_order;
    }

    public GameBoard get_gameboard() {
        return gameboard;
    }

    public soundController getSound() {
        return sound;
    }

    public ArrayList<Player> get_players() {
        return players;
    }

    public Player get_curr_player() {
        return curr_player;
    }
    public Boolean chip_has_been_interacted_with(PhysicalChip pchip) {

        return pchip.get_clover();
    }

    public Button get_takeButton() {
        return takeButton;
    }

    public Button get_leaveButton() {
        return leaveButton;
    }

    public PhysicalChip get_current_pchip() {
        return current_pchip;
    }

    public void set_current_pchip(PhysicalChip pc) {
        current_pchip = pc;
    }

    public void game_over() {
        // Enter Game over scene here!
        System.out.println("Game Over!");
        determine_winner();
    }
}
