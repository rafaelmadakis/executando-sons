package com.example.mediaplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Classe do MainActivity
 * @author Rafael Madakis
 * @since 2019
 */
public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekVolume;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bach);
        inicializarSeekBar();
    }

    /**
     * Método privado para iniciar o SeekBar
     * Configurar o audio manager
     * recuperar os valores de volume máximo e o volume atual
     * configurar os valores máximos para o seekBar
     * configurar o progresso atual do seekBar
     */
    private void inicializarSeekBar() {

        seekVolume = findViewById(R.id.seekVolume);

        //configurar o audio manager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //recupera os valores de volume máximo e o volume atual
        int volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //configurar os valores máximos para o SeekBar
        seekVolume.setMax(volumeMaximo);


        //configurando o progresso atual do seekBar
        seekVolume.setProgress(volumeAtual);

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    /**
     * Método para executar o audio ao apertar o play a música começa
     * a tocar to começo, salvo quando anteriormente for precisonado
     * o pause
     * @param view
     */
    public void executarSom(View view) {

        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    /**
     * Método para pausa a música e ao apertar o play a música
     * voltar a tocar de onde tinha pausado
     * @param view
     */
    public void pausarMusica(View view) {

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    /**
     * Método para parar a Musica e ao apertar o play a musica
     * começa a tocar do começo
     * @param view
     */
    public void pararMisica(View view) {

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bach);
        }
    }

    /**
     * Método para realizar alguma função ao fechar o app
     * Caso a activity tenha sido destruída, o
     * player irá parar com a música para liberar
     * recursos de media que estavam sendo usados
     * pelo MediaPlayer
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * Ciclo de vida da Activity
     * Remever este método caso queira deixar a
     * musica tocando com a tela do celular apagada
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
}