# Strecklistan-appen

## Instruktioner

### Komma igång
Att skapa Android-appar görs med fördel i Android Studio, som är nån slags variant av IntelliJ. Det är gratis att ladda ned.
I repot finns hela Android Studio-projektet, så det är bara att klona repot, och i Android Studio välja "Import project".

### Android-tips
Jag började själv experimentera med det här typ i förra veckan, så mitt bästa tips är typ "Googla Android development
tutorial". Däremot tänker jag att den här posten kan stå kvar, så kan vi fylla på den i takt med att vi upptäcker saker.

*Network on main thread error - om man ska göra operationer som kräver nätverksanslutning måste man utföra dem i en
egen tråd. Jag har gjort det här på två ställen i den existerande koden, dvs för login och för strecka, så bara att
kolla där för att se hur man kan göra.

### Strecklistan-info

#### API:t
Jag tänker mig att jag kommer att utföra det mesta av arbetet med själva API:et från Strecklistans sida. Ni kommer att
interagera med listan genom att skicka POST-requests till listan. Titta i koden för att se hur det görs. Jag har
implementerat klassen HTTP för att göra det så smidigt som möjligt. POST-parametern "do" sätts alltså alltid till "api",
och sedan specificerar man vilken data man vill ha genom parametern apiType. Övriga parametrar beror på vad man vill ha.

I nuläget finns de här funktionerna implementerade i API:t:

*apiType=getID - tar in till parameter, username, och returnerar användar-id för det användarnamnet.

#### Allmänt om strecklistan
##### SQL
"Under motorhuven" är Strecklistan en SQL-databas. Den har 5 tabeller, log, prices, streck, users och variables.

De tabeller som torde vara relevanta för oss är
*prices - info om olika strecktyper. id, name, price.
*streck - info om "vanliga" användare. id, nickname, name, email, balance, section (och lite till)
*log - alla streck som nånsin dragits. I skrivande stund dryga 50,000 rader (!). id, who, time, action, param, där
id alltså är ett id på själva strecket, who är id för streckaren, action är 1 om det är ett streck och 2 om det är
en insättning och param är hur mycket det kostade.

Vill man spana mer på databasen så är det bara att öppna den på Sqrubbendatorn. Sök på "mysql" i .bash_history i hemmappen
så kommer ni (efter ett tag) att hitta den rad som loggar in er på databasen, vill inte skriva den här pga innehåller
lösenord och sådant.

Uppdateringar till strecklistan utförs för det mesta genom att man använder några inbyggda funktioner, add_streck, undo, osv.
Jag har inte koll riktigt på hur man editerar SQL-funktioner, men ärligt talat vet jag inte riktigt varför vi skulle
behöva göra det.

##### Webbsidan
Webbgränssnittet är lite säreget, men mejkar sense när man väl tittat noga på det. I princip laddar man alltid om 
"index.php", men med olika värden på parametern "do". index.php inkluderar i början filen "update.php", som utför
många av transaktionerna till databasen. Så om man till exempel streckar, så är det man gör egentligen att ladda
index.php med do=streck. update.php körs, reagerar på att do=streck, utför uppdateringarna jämtemot SQL-databasen,
och skickar sedan vidare användaren till vanliga index.php.
