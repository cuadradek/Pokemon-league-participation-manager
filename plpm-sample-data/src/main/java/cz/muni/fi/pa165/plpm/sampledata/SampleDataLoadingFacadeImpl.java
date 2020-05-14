package cz.muni.fi.pa165.plpm.sampledata;

import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Pokemon;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.enums.PokemonType;
import cz.muni.fi.pa165.plpm.service.BadgeService;
import cz.muni.fi.pa165.plpm.service.GymService;
import cz.muni.fi.pa165.plpm.service.PokemonService;
import cz.muni.fi.pa165.plpm.service.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Jakub Doczy
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    private final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private GymService gymService;

    @Autowired
    private BadgeService badgeService;

    @Override
    @SuppressWarnings("unused")
    public void loadData() {
        GregorianCalendar.Builder dateBuilder = new GregorianCalendar.Builder();
        Trainer ash = trainer("ash", "Ash", "Ketchum",
                dateBuilder.setDate(1993, 10, 5).build(), "nbusr123", false);
        Trainer misty = trainer("mist", "Misty", "Kasumi",
                dateBuilder.setDate(1994, 12, 24).build(), "hunter2", false);
        Trainer brock = trainer("brock", "Brock", "Takeshi",
                dateBuilder.setDate(1992, 9, 5).build(), "password1", false);
        Trainer jessie = trainer("jess", "Jessica", "Musashi",
                dateBuilder.setDate(1985, 2, 5).build(), "jess", false);
        Trainer user = trainer("user", "Us", "Er",
                dateBuilder.setDate(1995, 1, 1).build(), "user", false);
        Trainer admin = trainer("admin", "Ad", "Min",
                dateBuilder.setDate(1995, 1, 1).build(), "admin", true);
        log.info("Loaded trainers.");

        Pokemon bulbasaur1 = pokemon("Bulbasaur", "Bulba", PokemonType.GRASS, 1, admin);
        Pokemon bulbasaur2 = pokemon("Bulbasaur", "Bisasam", PokemonType.GRASS, 5, null);
        Pokemon bulbasaur3 = pokemon("Bulbasaur", "Fushigidane",  PokemonType.GRASS, 10, ash);
        Pokemon pikachu1 = pokemon("Pikachu", "Pika", PokemonType.ELECTRIC, 1, ash);
        Pokemon pikachu2 = pokemon("Pikachu", "Pikapika", PokemonType.ELECTRIC, 10, null);
        Pokemon meowth1 = pokemon("Meowth", "Mr Pirate", PokemonType.NORMAL, 7, jessie);
        Pokemon meowth2 = pokemon("Meowth", "Clawdia", PokemonType.NORMAL, 12, user);
        Pokemon meowth3 = pokemon("Meowth", "Lancelot", PokemonType.NORMAL, 11, null);
        Pokemon ivysaur = pokemon("Ivysaur", "Fushigisou", PokemonType.GRASS, 22, misty);
        Pokemon venusaur = pokemon("Venusaur", "Fushigibana", PokemonType.GRASS, 99, admin);
        Pokemon charmander = pokemon("Charmander", "Hitokage", PokemonType.FIRE, 2, ash);
        Pokemon charmeleon = pokemon("Charmeleon", "Lizardo", PokemonType.FIRE, 25, jessie);
        Pokemon charizard = pokemon("Carizard", "Dragon", PokemonType.DRAGON, 100, admin);
        Pokemon squirtle = pokemon("Squirtle", "Schiggy", PokemonType.WATER, 10, misty);
        Pokemon wartortle = pokemon("Wartortle", "Carabaffe", PokemonType.WATER, 50, misty);
        Pokemon blastoise = pokemon("Blastoise", "Turtok", PokemonType.WATER, 100, admin);
        Pokemon caterpie = pokemon("Caterpie", "Chenipan", PokemonType.BUG, 3, jessie);
        Pokemon metapod = pokemon("Metapod", "Chrysacier", PokemonType.BUG, 23, null);
        Pokemon butterfree = pokemon("Butterfree", "Papilusion", PokemonType.BUG, 77, null);
        Pokemon weedle = pokemon("Weedle", "Hornliu", PokemonType.BUG, 6, null);
        Pokemon kakuna = pokemon("Kakuna", "Coconfort", PokemonType.BUG, 45, user);
        Pokemon beedrill = pokemon("Beedrill", "Spear", PokemonType.BUG, 90, null);
        Pokemon onix = pokemon("Onix", "Rock", PokemonType.ROCK, 5, brock);
        log.info("Loaded pokemons.");

        Gym ceruleanGym = gym("Cerulean City", misty, PokemonType.WATER);
        Gym pewterGym = gym("Pewter City", brock, PokemonType.ROCK);
        Gym adminsGym = gym("Admin City", admin, PokemonType.DRAGON);
        log.info("Loaded gyms.");

        Badge adminsCeruleanBadge = badge(admin, ceruleanGym);
        Badge adminsPewterBadge = badge(admin, pewterGym);
        Badge mistysPewterBadge = badge(misty, pewterGym);
        Badge brocksCeruleanBadge = badge(brock, ceruleanGym);
        Badge ashesCeruleanBadge = badge(ash, ceruleanGym);
        Badge ashesPewterBadge = badge(ash, pewterGym);
        Badge ashesAdminBadge = badge(ash, adminsGym);
        log.info("Loaded badges.");
    }

    private Pokemon pokemon(String name, String nickname, PokemonType type, int level, Trainer trainer) {
        Pokemon pokemon = new Pokemon.Builder().name(name).nickname(nickname).trainer(trainer)
                .type(type).level(level).build();
        pokemon = pokemonService.createPokemon(pokemon);
        return pokemon;
    }

    private Trainer trainer(String nickname, String firstName, String lastName, Calendar birthDate, String password, boolean admin) {
        Trainer trainer = new Trainer.Builder().nickname(nickname).firstName(firstName).lastName(lastName)
                .birthDate(birthDate.getTime()).password(password).isAdmin(admin).build();
        trainer = trainerService.createTrainer(trainer);
        return trainer;
    }

    private Gym gym(String city, Trainer leader, PokemonType type) {
        Gym gym = new Gym.Builder().city(city).leader(leader).type(type).build();
        gymService.createGym(gym);
        return gym;
    }

    private Badge badge(Trainer trainer, Gym gym) {
        Badge badge = new Badge(trainer, gym);
        badgeService.createBadge(badge);
        return badge;
    }
}