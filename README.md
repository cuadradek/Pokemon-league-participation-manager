# Pokemon-league-participation-manager (PLPM)
[See our Wiki page](https://github.com/cuadradek/Pokemon-league-participation-manager/wiki)


### Build
```
mvn clean install
```
### Web
```
cd plpm-web
mvn cargo:run
```
Website will be available at `http://localhost:8080/pa165/`

### Rest
```
cd plpm-rest
mvn cargo:run
```
#####Create new pokemon
```
curl --request POST --url http://localhost:8080/pa165/rest/pokemons/create --header "Content-Type:application/json" --data "{\"name\":\"Pokemon\",\"nickname\":\"Pocky\",\"type\":\"GHOST\"}"
```
#####Delete pokemon
```
curl --request DELETE --url http://localhost:8080/pa165/rest/pokemons/delete/2
```
#####Change pokemon level
```
curl --request PUT --url http://localhost:8080/pa165/rest/pokemons/1/change-level/2
```
#####Get pokemon by ID
```
curl --request GET --url http://localhost:8080/pa165/rest/pokemons/1
```
#####Get pokemons by name
```
curl --request GET --url http://localhost:8080/pa165/rest/pokemons/get-by-name/Bulbasaur
```
#####Get pokemons by nickname
```
curl --request GET --url http://localhost:8080/pa165/rest/pokemons/get-by-nickname/Bulba
```
#####Get pokemons by trainer ID
```
curl --request GET --url http://localhost:8080/pa165/rest/pokemons/get-by-trainer/1
```
#####Get all pokemons
```
curl --request GET --url http://localhost:8080/pa165/rest/pokemons
```
### Default users
