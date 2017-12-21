package pl.edu.agh.bd2;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;

public class Solution {

    private final GraphDatabase graphDatabase = GraphDatabase.createDatabase();

    public void databaseStatistics() {
        System.out.println(graphDatabase.runCypher("CALL db.labels()"));
        System.out.println(graphDatabase.runCypher("CALL db.schema()"));
        System.out.println(graphDatabase.runCypher("CALL db.relationshipTypes()"));
    }

    public void dropDatabase(){
        graphDatabase.deleteAllData();
    }
    public String getAllRelationshipsOfNode(int id){
        return graphDatabase.runCypher(String.format("MATCH (m)-[r]-(n) WHERE ID(m) = %d RETURN m,r,n", id));
    }

    public String pathBetweenNodes(int id1, int id2) {
        return graphDatabase.runCypher(String.format("MATCH (m), (n), p=shortestPath((m)-[*..]-(n)) " +
                "WHERE ID(m) = %d AND ID(n) = %d " +
                "RETURN p",id1, id2));//
    }

    public void populateDatabase() {
        GraphDatabaseService graphDatabaseService = graphDatabase.getGraphDatabaseService();
        try (Transaction transaction = graphDatabaseService.beginTx()) {
            //***MUSICALS***//
            Node lesMiserables = createMusical(graphDatabaseService, "Les Miserables",
                    "1980", "Les Misérables by Victor Hugo");
            Node thePhantomOfTheOpera = createMusical(graphDatabaseService, "The Phantom Of The Opera",
                    "1986", "Le Fantôme de l'Opéra by Gaston Leroux");
            Node wicked = createMusical(graphDatabaseService, "Wicked", "2003",
                    "Wicked: The Life and Times of the Wicked Witch of the West by Gregory Maguire");
            Node evita = createMusical(graphDatabaseService, "Evita", "1978",
                    "Original");
            Node cats = createMusical(graphDatabaseService, "Cats", "1981",
                    "Old Possum's Book of Practical Cats by T. S. Eliot");
            Node sunsetBoulevard = createMusical(graphDatabaseService, "Sunset Boulevard",
                    "1993", "1950 film Sunset Blvd.");
            Node oklahoma = createMusical(graphDatabaseService, "Oklahoma!", "1943",
                    "Lynn Riggs' play Green Grow the Lilacs");
            Node theKingAndI = createMusical(graphDatabaseService, "The King and I", "1951",
                    "Anna and the King of Siam by Margaret Landon");
            Node theSoundOfMusic = createMusical(graphDatabaseService, "The Sound of Music", "1959",
                    "1956 German film Die Trapp-Familie and Maria von Trapp's autobiography The Story of the Trapp Family Singers");
            Node hamilton = createMusical(graphDatabaseService, "Hamilton", "2015", "Alexander Hamilton by Ron Chernow");

            //***Composers***//
            Node andrewLloydWebber = createComposer(graphDatabaseService, "Andrew", "Lloyd Webber",
                    "69", "Male");
            Node claudeMichelSchonberg = createComposer(graphDatabaseService, "Claude-Michel", "Schönberg",
                    "74", "Male");
            Node richardRodgers = createComposer(graphDatabaseService, "Richard", "Rodgers",
                    "dead", "Male");
            Node stephenSchwartz = createComposer(graphDatabaseService, "Stephen", "Schwartz",
                    "69", "Male");
            Node linManuelMiranda = createComposer(graphDatabaseService, "Lin Manuel", "Miranda",
                    "37", "Male");
            //***Lyricists**//
            stephenSchwartz.addLabel(() -> "Lyricist");
            Node herbertKretzmer = createLyricists(graphDatabaseService, "Herbert", "Kretzmer",
                    "92", "Male");
            Node charlesHart = createLyricists(graphDatabaseService, "Charles", "Hart", "56", "Male");
            Node timRice = createLyricists(graphDatabaseService, "Tim", "Rice", "73", "Male");
            Node oscarHammersteinII = createLyricists(graphDatabaseService, "Oscar", "Hammerstein II",
                    "dead", "Male");
            linManuelMiranda.addLabel(() -> "Lyricist");

            //***Actors***//
            Node pattiLuPone = createActor(graphDatabaseService, "Patti", "LuPone", "68", "Female");
            Node idinaMenzel = createActor(graphDatabaseService, "Idina", "Menzel", "46", "Female");
            Node colmWilkinson = createActor(graphDatabaseService, "Colm", "Wilkinson", "73", "Male");
            Node michaelCrawford = createActor(graphDatabaseService, "Michael", "Crawford", "75", "Male");
            Node raminKarimloo = createActor(graphDatabaseService, "Ramin", "Karimloo", "39", "Male");
            Node sierraBoggess = createActor(graphDatabaseService, "Sierra", "Boggess", "35", "Female");
            linManuelMiranda.addLabel(() -> "Actor");
            //***Theatres***//
            Node gershwinTheatre = createTheater(graphDatabaseService, "Gershwin Theatre", "W. 51st St.", "1933");
            Node majesticTheatre = createTheater(graphDatabaseService, "Majestic Theatre", "W. 44th St.", "1645");
            Node neilSimonTheatre = createTheater(graphDatabaseService,"Neil Simon Theatre","W. 52nd St.","1467");
            Node richardRodgersTheatre = createTheater(graphDatabaseService, "Richard Rodgers Theatre","W. 46th St.","1400");
            Node ambassadorTheatre = createTheater(graphDatabaseService, "Ambassador Theatre","W. 49th St. ","1125");

            //***Play in Relationship**//
            createPlayedInRelationship(pattiLuPone,evita);
            createPlayedInRelationship(pattiLuPone,lesMiserables);
            createPlayedInRelationship(pattiLuPone,sunsetBoulevard);
            createPlayedInRelationship(linManuelMiranda,hamilton);
            createPlayedInRelationship(sierraBoggess,thePhantomOfTheOpera);
            createPlayedInRelationship(idinaMenzel,wicked);
            createPlayedInRelationship(colmWilkinson,lesMiserables);
            createPlayedInRelationship(michaelCrawford,thePhantomOfTheOpera);
            createPlayedInRelationship(raminKarimloo,lesMiserables);
            createPlayedInRelationship(raminKarimloo,thePhantomOfTheOpera);
            createPlayedInRelationship(raminKarimloo,sunsetBoulevard);

            //***Composed Relationship **//
            createComposedMusicTo(andrewLloydWebber,thePhantomOfTheOpera);
            createComposedMusicTo(andrewLloydWebber,sunsetBoulevard);
            createComposedMusicTo(andrewLloydWebber,cats);
            createComposedMusicTo(stephenSchwartz,wicked);
            createComposedMusicTo(linManuelMiranda,hamilton);
            createComposedMusicTo(claudeMichelSchonberg,lesMiserables);
            createComposedMusicTo(andrewLloydWebber,evita);
            createComposedMusicTo(richardRodgers,theKingAndI);
            createComposedMusicTo(richardRodgers,theSoundOfMusic);
            createComposedMusicTo(richardRodgers,oklahoma);

            //***Written Lyrics Relationship **//
            createWrittenLyricsTo(herbertKretzmer,lesMiserables);
            createWrittenLyricsTo(charlesHart,thePhantomOfTheOpera);
            createWrittenLyricsTo(timRice,evita);
            createWrittenLyricsTo(oscarHammersteinII,theKingAndI);
            createWrittenLyricsTo(oscarHammersteinII,theSoundOfMusic);
            createWrittenLyricsTo(oscarHammersteinII,oklahoma);
            createWrittenLyricsTo(linManuelMiranda,hamilton);


            //***Was Staged At Relationship ***//
            createWasStagedAt(wicked,gershwinTheatre);
            createWasStagedAt(thePhantomOfTheOpera,majesticTheatre);
            createWasStagedAt(theKingAndI,gershwinTheatre);
            createWasStagedAt(hamilton,neilSimonTheatre);
            createWasStagedAt(lesMiserables,richardRodgersTheatre);
            createWasStagedAt(evita,ambassadorTheatre);
            createWasStagedAt(cats,ambassadorTheatre);
            createWasStagedAt(oklahoma,richardRodgersTheatre);
            createWasStagedAt(theSoundOfMusic,majesticTheatre);
            createWasStagedAt(sunsetBoulevard,neilSimonTheatre);
            transaction.success();

        }
    }


    private Node createActor(GraphDatabaseService graphDatabaseService, String firstName, String lastName, String age, String sex) {
        Node actor = graphDatabaseService.createNode();
        actor.addLabel(() -> "Actor");
        actor.addLabel(() -> "Human");
        actor.setProperty("firstName", firstName);
        actor.setProperty("lastName", lastName);
        actor.setProperty("age", age);
        actor.setProperty("sex", sex);
        return actor;
    }

    private Node createTheater(GraphDatabaseService graphDatabaseService, String name, String location, String numberOfSeats) {
        Node theater = graphDatabaseService.createNode();
        theater.addLabel(() -> "Theater");
        theater.setProperty("name", name);
        theater.setProperty("location", location);
        theater.setProperty("numberOfSeats", numberOfSeats);
        return theater;
    }

    private Node createComposer(GraphDatabaseService graphDatabaseService, String firstName, String lastName, String age, String sex) {
        Node composer = graphDatabaseService.createNode();
        composer.addLabel(() -> "Composer");
        composer.addLabel(() -> "Human");
        composer.setProperty("firstName", firstName);
        composer.setProperty("lastName", lastName);
        composer.setProperty("age", age);
        composer.setProperty("sex", sex);
        return composer;
    }

    private Node createLyricists(GraphDatabaseService graphDatabaseService, String firstName, String lastName, String age, String sex) {
        Node lyricists = graphDatabaseService.createNode();
        lyricists.addLabel(() -> "Lyricists");
        lyricists.addLabel(() -> "Human");
        lyricists.setProperty("firstName", firstName);
        lyricists.setProperty("lastName", lastName);
        lyricists.setProperty("age", age);
        lyricists.setProperty("sex", sex);
        return lyricists;
    }

    private Node createMusical(GraphDatabaseService graphDatabaseService, String title, String firstProductionYear, String basis) {
        Node musical = graphDatabaseService.createNode();
        musical.addLabel(() -> "Musical");
        musical.setProperty("title", title);
        musical.setProperty("year", firstProductionYear);
        musical.setProperty("basis", basis);
        return musical;
    }

    private void createPlayedInRelationship(Node actor, Node musical) {
        actor.createRelationshipTo(musical, RelationshipType.withName("PLAYED IN"));
    }

    private void createWrittenLyricsTo(Node lyricists, Node musical) {
        lyricists.createRelationshipTo(musical, RelationshipType.withName("WROTE LYRICS TO"));
    }

    private void createComposedMusicTo(Node composer, Node musical) {
        composer.createRelationshipTo(musical, RelationshipType.withName("COMPOSED MUSIC TO"));
    }

    private void createWasStagedAt(Node musical, Node theater) {
        musical.createRelationshipTo(theater, RelationshipType.withName("WAS STAGED AT"));
    }
}
