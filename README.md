# pokemon-finder

This project is using MVN for build tool. 

When you start the application, all pokemons will be fetched from the external API (https://pokeapi.co/api/v2/) in order
to save them into our H2 database. This can be easily switched to a regular database such that we do not put a heavy 
load on the external pokemon API on each startup. 

You can query the API using the /pokemon endpoint with the following params:
- limit (int)
- sortBy (string)

e.g. /pokemon?limit=5&sortBy=height will provide you the 5 tallest pokemons. 

Possible sorting fields:
- height
- weight
- baseExperience
- name