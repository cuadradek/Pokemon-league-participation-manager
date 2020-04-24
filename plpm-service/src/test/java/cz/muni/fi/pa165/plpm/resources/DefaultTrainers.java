package cz.muni.fi.pa165.plpm.resources;

import cz.muni.fi.pa165.plpm.entity.Trainer;

import java.util.Date;

public class DefaultTrainers {

    public static Trainer getAsh() {
        Trainer trainerAsh = new Trainer();
        trainerAsh.setId(1L);
        trainerAsh.setFirstName("Ash");
        trainerAsh.setLastName("Ketchum");
        trainerAsh.setNickname("Satoshi");
        trainerAsh.setBirthDate(new Date(0));
        // the most secure password in history: nbusr123
        trainerAsh.setPassword("7751E53856859902F722F43ACC6AF7FD1A55489DAA9A57C6E370DF9372988258");
        return trainerAsh;
    }

    public static Trainer getGary() {
        Trainer trainerGary = new Trainer();
        trainerGary.setId(2L);
        trainerGary.setFirstName("Gary");
        trainerGary.setLastName("Oak");
        trainerGary.setNickname("Shigeru");
        trainerGary.setBirthDate(new Date(0));
        trainerGary.setAdmin(true);
        //password: Correct horse battery staple
        trainerGary.setPassword("53B2405B26A4C9F8F073ED032539F15A9CBF94ED1254D52A72CAD07B1941C1D7");
        return trainerGary;
    }

    public static Trainer getTracey() {
        Trainer trainerTracey = new Trainer();
        trainerTracey.setId(3L);
        trainerTracey.setFirstName("Tracey");
        trainerTracey.setLastName("Sketchit");
        trainerTracey.setNickname("Kenji");
        trainerTracey.setBirthDate(new Date(0));
        // password hunter2
        trainerTracey.setPassword("F52FBD32B2B3B86FF88EF6C490628285F482AF15DDCB29541F94BCF526A3F6C7");
        return trainerTracey;
    }

}
