# Beschrijving van de app #

Chronologie is een educatief spel gebaseerd op het bordspel timeline. Spelers proberen items uit de Open Cultuur Data API op chronologische volgorde te rangschikken op een tijdlijn. De items nemen de vorm aan van kaarten, als de speler een kaart op de correcte positie in de tijdlijn weet te plaatsen gaat zijn score omhoog. Naarmate het spel vordert wordt het steeds moeilijker de correcte positie te bepalen op de tijdlijn er liggen inmiddels al kaarten en er zijn dus meer plekken om de volgende kaart te plaatsen.

# Doelgroep #

De applicatie kan op diversen manieren worden ingezet. Cultuur liefhebbers kunnen kiezen uit diversen thema's elk met een eigen set kaarten. Ze kunnen het spel alleen spelen om hun kennis over de cultuurgeschiedenis te testen of ze kunnen iemand anders uitdagen om het spel competitief te spelen.

Kaart sets rondom een bepaalde thema's kunnen worden samengesteld door de gebruikers zelf en worden gedeeld met anderen. Hierdoor zullen er constant nieuwe uitdaging voor de spelers beschikbaar komen. Deze functionaliteit kan ook worden gebruikt door deelnemende organisaties welke datasets aanleveren voor de Open Cultuur Data API, denk bijvoorbeeld aan een museum dat een kaart set samenstelt rondom de huidige tentoonstelling of actualiteit.

Naast toepassingen die de zichtbaarheid van de objecten in de deelnemende datasets vergroot, kan het spel ook worden ingezet als aanvullend lesmateriaal bij educatie welke zich richt op lesstof dat raakvlakken heeft met de deelnemede datasets.

# Technische implementatie #

Het prototype is gerealiseerd als een web applicatie dat binnen de gangbare desktop browsers kan worden gebruikt. Het gebruik van web-standaarden maakt het mogelijk de applicatie in de toekomst geschikt te maken voor alle apparaten die ondersteuning bieden voor web-technologie. Denk bijvoorbeeld aan smart phones en tablets. Daarnaast is de keuze voor web-standaarden  gemotiveerd door het feit dat deze standaarden veel van hun idealen delen met open data.

De overgrote deel van de applicatie is gerealiseerd met behulp van de open source programmeertalen Clojure en ClojureScript. Deze talen zijn ontworpen om het ontwikkelen van relatief complexe applicaties te versimpelen.

# Doorontwikkeling prototype #

Het huidige prototype bevat de complete logica voor het spelen van een één speler spel. Er is in het prototype slechts één voorbeeld kaart set beschikbaar om mee te spelen. Wanneer het prototype word uitgekozen door de jury van de Open Cultuur Data Challenge om tot een volledige applicatie te worden ontwikkeld wil ik de volgende verbeteringen gaan realiseren.

1. De user interface is momenteel slechts ontwikkeld tot op het punt dat de applicatie werkzaam is. Tijdens de doorontwikkeling zal een volwaardige interface worden gerealiseerd die zowel aantrekkelijk als gebruiksvriendelijk zal zijn. Om dat te bewerkstelligen zal ik de hulp inschakelen van een interaction designer.

2. De volledige applicatie zal de mogelijkheid bieden het spel met meerdere spelers te spelen. De initiator voegt andere spelers toe doormiddel van hun e-mail adres. Na de start van het spel kunnen de deelnemers één voor één kaarten plaatsen totdat er geen kaarten meer over zijn in de gekozen kaart set. Spelers ontvangen mail notificaties wanneer hun beurt is.

3. Nieuwe kaart sets rondom een thema kunnen worden samengesteld door met diverse zoekvelden items uit de beschikbare datasets te selecteren. Deze sets kunnen publiekelijk met andere spelers worden gedeeld. Er zal een overzicht zijn van populaire kaart sets om uit te kiezen.

# Gebruikte datasets #

De applicatie kan gebruik maken van alle datasets die op datum gesorteerd kunnen worden. Het prototype bevat een voorbeeld kaart set met daarin schilderijen uit o.a. het Rijksmuseum en het Amsterdam Museum. De uiteindelijke applicatie zal functionaliteit bevatten waarmee de gebruikers zelf nieuwe combinaties van kaarten kunnen samenstellen uit de beschikbare datasets.
