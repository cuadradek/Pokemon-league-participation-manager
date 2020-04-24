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
        trainerAsh.setAdmin(true);
        // the most secure password in history: nbusr123
        trainerAsh.setPassword("$2a$10$lw9RnJkD51y3wpPX.rL.9uJqDJx3UtIbGBqWYFUFdpQmV8JJJxrVW");
        return trainerAsh;
    }

    public static Trainer getGary() {
        Trainer trainerGary = new Trainer();
        trainerGary.setId(2L);
        trainerGary.setFirstName("Gary");
        trainerGary.setLastName("Oak");
        trainerGary.setNickname("Shigeru");
        trainerGary.setBirthDate(new Date(0));
        trainerGary.setAdmin(false);
        //password: Correct horse battery staple
        trainerGary.setPassword("$2a$10$mf/1ajQPmo/3iST48OcsNOSNV/jaWJ4VKVkK1lTtfg4L9hhanXmI6");
        return trainerGary;
    }

    public static Trainer getTracey() {
        Trainer trainerTracey = new Trainer();
        trainerTracey.setId(3L);
        trainerTracey.setFirstName("Tracey");
        trainerTracey.setLastName("Sketchit");
        trainerTracey.setNickname("Kenji");
        trainerTracey.setBirthDate(new Date(0));
        trainerTracey.setAdmin(false);
        // password hunter2
        trainerTracey.setPassword("$2a$10$SFRU65wn5y3Iao3Cpo19Fua2g6yJ3NaYl52VolohwvcsliKGUvPFK");
        return trainerTracey;
    }

    public static String getPlainPasswordAsh() {
        return "nbusr123";
    }

    public static String getPlainPasswordGary() {
        return "Correct horse battery staple";
    }

    public static String getPlainPasswordTracey() {
        return "hunter2";
    }
}
