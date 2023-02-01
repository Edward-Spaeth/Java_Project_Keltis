package com.KeltisT.Players;

import com.KeltisT.Chips.Chip;
import com.KeltisT.Chips.PhysicalChip;

import java.util.ArrayList;
import java.util.Arrays;

public class  Stack {
    private ArrayList<PhysicalChip> pchips;
    private int direction; // -1 = Descending, 0 = Neutral, 1 = Ascending
    private int bound_val;
    private ArrayList<PhysicalChip> dummychips;
    Boolean player_is_on_right_side;

    Stack(){
        pchips = new ArrayList<>();
        direction = 0;
        bound_val = -1;
    }

    public void set_dummychips(ArrayList<PhysicalChip> dchips) {
        dummychips = dchips;
    }

    public void insert(PhysicalChip pc){
        // Ideally if check is not necessary
        if (check_if_insert_possible(pc)){
            PhysicalChip corresponding_dummy;
            dummychips.get(pchips.size()).set_dummy(pc.get_value(), pc.get_color(), pc.get_clover(), pc.get_wish(), pc.get_bonus());
            System.out.println("pchips.size() is " + pchips.size());
            pchips.add(pc);
            if (pchips.size() > 1 && direction == 0){
                if (bound_val < pc.get_value()){
                    direction = 1;
                }
                else{
                    direction = -1;
                }
            }
            bound_val = pc.get_value();
        }
        else {
            System.out.println("Insert not possible!\nChips:");
            for (PhysicalChip pchip : pchips) {
                System.out.print(pchip.get_value() + " ");
            }
            System.out.println("\nSize: " + pchips.size());
            int direction_of_desired_insertion;
            if (bound_val < pc.get_value()){
                direction_of_desired_insertion = 1;
            }
            else{
                direction_of_desired_insertion = -1;
            }
            System.out.println("\nDirection of stack: " + direction_of_desired_insertion);
            System.out.println("\nWanted direction: " + direction);
        }
    }
    public Boolean check_if_insert_possible(PhysicalChip pc){
        // First move is always valid.
        if (pchips.size() == 0){
            return Boolean.TRUE;
        }
        else {
            // Calculate in which direction the user wants to insert.
            int direction_of_desired_insertion;
            if (bound_val < pc.get_value()){
                direction_of_desired_insertion = 1;
            }
            else{
                direction_of_desired_insertion = -1;
            }
            // For the second chip, the direction has not yet been decided.
            // This chip will now decide the direction.
            if (direction == 0){
                return Boolean.TRUE;
            }
            else{
                // Direction was already decided and intent aligns with restriction. Valid move.
                if (direction_of_desired_insertion == direction){
                    return Boolean.TRUE;
                }
                // Direction was already decided and intent does not align with restriction. Invalid move.
                else{
                    return Boolean.FALSE;
                }
            }
        }
    }

    public int count_wishes(){
        int amt_wishes = 0;
        for (Chip c : pchips){
            if (c.get_wish()){
                amt_wishes++;
            }
        }
        return amt_wishes;
    }

    public int count_bonus_points(){
        int sum_bonus_points = 0;
        for (Chip c : pchips){
            sum_bonus_points += c.get_bonus();
        }
        return sum_bonus_points;
    }

    public int count_chips(){
        return pchips.size();
    }
}
