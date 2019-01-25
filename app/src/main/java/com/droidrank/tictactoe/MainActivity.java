package com.droidrank.tictactoe;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String[] blocks = new String[9];
    private Player currentPlayer = Player.ONE;
    private int turnsCount = 0;

    Button block1, block2, block3, block4, block5, block6, block7, block8, block9, restart;
    TextView result;
    AlertDialog alertDialog;

    private View.OnClickListener onBlockClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onBlockClicked((Button) view);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void restart() {
        turnsCount = 0;
        currentPlayer = Player.ONE;
        blocks = new String[9];
        block1.setText("");
        block2.setText("");
        block3.setText("");
        block4.setText("");
        block5.setText("");
        block6.setText("");
        block7.setText("");
        block8.setText("");
        block9.setText("");
        result.setText("");
        enableBlocks(true);
        restart.setText(R.string.restart_button_text_initially);
    }

    private Winner checkWinner() {
        String[] lines = setupLines();
        boolean playerOneWins = false;
        boolean playerTwoWins = false;

        for (int i = 0; i < 8; i++) {
            if (lines[i].equalsIgnoreCase("XXX"))
                playerTwoWins = true;
            else if (lines[i].equalsIgnoreCase("OOO")) {
                playerOneWins = true;
            }
        }

        if (playerOneWins && playerTwoWins) {
            return Winner.TIE;
        } else if (playerOneWins) {
            return Winner.ONE;
        } else if (playerTwoWins) {
            return Winner.TWO;
        }

        if (turnsCount == 9)
            return Winner.TIE;

        return Winner.NONE;
    }

    private String[] setupLines() {
        String[] lines = new String[8];
        // vertical
        lines[0] = blocks[0] + blocks[1] + blocks[2];
        lines[1] = blocks[3] + blocks[4] + blocks[5];
        lines[2] = blocks[6] + blocks[7] + blocks[8];

        // horizontal
        lines[3] = blocks[0] + blocks[3] + blocks[6];
        lines[4] = blocks[1] + blocks[4] + blocks[7];
        lines[5] = blocks[2] + blocks[5] + blocks[8];

        // diagonal
        lines[6] = blocks[0] + blocks[4] + blocks[8];
        lines[7] = blocks[2] + blocks[4] + blocks[6];

        return lines;
    }

    private void setupViews() {
        block1 = (Button) findViewById(R.id.bt_block1);
        block2 = (Button) findViewById(R.id.bt_block2);
        block3 = (Button) findViewById(R.id.bt_block3);
        block4 = (Button) findViewById(R.id.bt_block4);
        block5 = (Button) findViewById(R.id.bt_block5);
        block6 = (Button) findViewById(R.id.bt_block6);
        block7 = (Button) findViewById(R.id.bt_block7);
        block8 = (Button) findViewById(R.id.bt_block8);
        block9 = (Button) findViewById(R.id.bt_block9);
        result = (TextView) findViewById(R.id.tv_show_result);
        restart = (Button) findViewById(R.id.bt_restart_game);
        setupBlocksClick();

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (turnsCount == 0)
                    restart();
                else
                    alertDialog.show();
            }
        });

        alertDialog = createDialog();
    }

    private AlertDialog createDialog() {
        return new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(R.string.restart_message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        restart();
                    }
                })
                .setNegativeButton("CANCEL", null)
                .create();
    }

    private void enableBlocks(boolean enable) {
        block1.setEnabled(enable);
        block2.setEnabled(enable);
        block3.setEnabled(enable);
        block4.setEnabled(enable);
        block5.setEnabled(enable);
        block6.setEnabled(enable);
        block7.setEnabled(enable);
        block8.setEnabled(enable);
        block9.setEnabled(enable);
    }

    private void setupBlocksClick() {
        block1.setOnClickListener(onBlockClick);
        block2.setOnClickListener(onBlockClick);
        block3.setOnClickListener(onBlockClick);
        block4.setOnClickListener(onBlockClick);
        block5.setOnClickListener(onBlockClick);
        block6.setOnClickListener(onBlockClick);
        block7.setOnClickListener(onBlockClick);
        block8.setOnClickListener(onBlockClick);
        block9.setOnClickListener(onBlockClick);
    }

    private void onBlockClicked(Button view) {
        if (!checkIsAlreadyFilled(view)) {
            turnsCount++;
            restart.setText(R.string.restart_button_text_in_middle_of_game);
            handleMovement(view.getId());
            Winner winner = checkWinner();

            switch (winner) {
                case NONE:
                    setNextPlayer();
                    break;
                case ONE:
                    setPlayerOneWins();
                    break;
                case TWO:
                    setPlayerTwoWins();
                    break;
                case TIE:
                    setPlayersTied();
                    break;
            }
        }
    }

    private void setPlayersTied() {
        enableBlocks(false);
        restart.setText(R.string.restart_button_text_initially);
        result.setText(R.string.draw);
        turnsCount = 0;
    }

    private void setPlayerTwoWins() {
        enableBlocks(false);
        restart.setText(R.string.restart_button_text_initially);
        result.setText(R.string.player_2_wins);
        turnsCount = 0;
    }

    private void setPlayerOneWins() {
        enableBlocks(false);
        restart.setText(R.string.restart_button_text_initially);
        result.setText(R.string.player_1_wins);
        turnsCount = 0;
    }

    private void setNextPlayer() {
        result.setText("");
        enableBlocks(true);
        currentPlayer = currentPlayer == Player.ONE ? Player.TWO : Player.ONE;
    }

    private void handleMovement(int id) {
        switch (id) {
            case R.id.bt_block1:
                blocks[0] = getMovement();
                block1.setText(blocks[0]);
                break;
            case R.id.bt_block2:
                blocks[1] = getMovement();
                block2.setText(blocks[1]);
                break;
            case R.id.bt_block3:
                blocks[2] = getMovement();
                block3.setText(blocks[2]);
                break;
            case R.id.bt_block4:
                blocks[3] = getMovement();
                block4.setText(blocks[3]);
                break;
            case R.id.bt_block5:
                blocks[4] = getMovement();
                block5.setText(blocks[4]);
                break;
            case R.id.bt_block6:
                blocks[5] = getMovement();
                block6.setText(blocks[5]);
                break;
            case R.id.bt_block7:
                blocks[6] = getMovement();
                block7.setText(blocks[6]);
                break;
            case R.id.bt_block8:
                blocks[7] = getMovement();
                block8.setText(blocks[7]);
                break;
            case R.id.bt_block9:
                blocks[8] = getMovement();
                block9.setText(blocks[8]);
                break;
        }
    }

    private String getMovement() {
        switch (currentPlayer) {
            case ONE:
                return getString(R.string.player_1_move);
            default:
                return getString(R.string.player_2_move);
        }
    }

    private boolean checkIsAlreadyFilled(Button view) {
        if (view.getText().toString().length() > 0)
            return true;
        else return false;
    }
}