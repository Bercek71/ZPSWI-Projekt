# Procesní postupy pro Agile vývoj - Hotelový rezervační systém

## 1. Sběr a správa požadavků (Product Backlog)

- **Správa backlogu požadavků:** 
 Požadavky od zákazníka budou přidány do backlogu v nástroji Jira. Každý požadavek bude popsán formou user story s jasnými kritérii přijetí. Pravidelně budeme kontrolovat, zda jsou všechny položky aktuální, a podle potřeby přidáme nové informace nebo upravíme stávající položky.

- **Aktualizace a změny v požadavcích:** 
 Aktualizace backlogu budou prováděny po každé retrospektivě, nebo po cviku. Pro změny bude zaveden mechanismus change requestů, které projdou schválením před začleněním do backlogu. Postup bude následující. 
Zadání a přesná formulace požadavků na změnu
Posouzení (identifikace rizik, odhad náročnosti)
Schválení
Prioritizace (pokud se bude jednat o kritickou změnu implementujeme okamžitě)
Implementace
Testování
Nasazení
Retrospektiva

- **Komunikace o prioritách:** 
 Pravidelné schůzky jednou týdně se zákazníkem budou určeny pro diskuzi priorit user stories. Na těchto schůzkách se prioritizují stories podle technických omezení. (Nemůžeme řešit webAPI před vytvořením DB)

- **Revize backlogu:** 
 Backlog bude revidován minimálně před každým sprintem. Navíc bude po každém sprintu zhodnocen a upraven podle zpětné vazby.

- **Definition of Done (DoD):** 
 DoD bude definováno pro každý inkrement na začátku sprintu. Typicky bude zahrnovat splnění kritérií přijetí, testování (unit, integrační testy), code review a dokumentaci.

## 2. Plánování sprintů a Product Increments

- **Plánování sprintů:** 
 Sprinty budou plánovány na začátku každého cyklu (dvoutýdenního sprintu). Práce bude rozdělena na dva týmy
- Backend starající se o WebAPI a databázi
- Frontend zaměřující se na webovou aplikaci

- **Určování user stories pro sprint:** 
 Pomocí techniky odhadování **Planning Poker** určíme, které user stories jsou reálné dokončit během sprintu. Prioritizace bude založena na dohodě mezi týmy.

- **Zajištění funkčních výstupů:** 
 Na konci každého sprintu bude každý inkrement plně funkční, otestovaný a připravený k integraci. Použijeme CI/CD pipeline k automatickému nasazování a testování výstupů.

- **Sprint review a retrospektivy:** 
 Na konci každého sprintu proběhne sprint review s prezentací výsledků na cviku a retrospektiva pro tým, kde se bude hodnotit, co fungovalo a co je potřeba zlepšit a budeme přicházet na lepší způsoby, jakými nadále postupovat ve vývoji a kterým vývojovým technikám se raději vyvarovat.

## 3. Implementace (Iterace vývoje)

- **Sledování průběhu sprintů:** 
 Pro sledování pokroku budeme používat Kanban board, který bude zobrazovat stav jednotlivých user stories (to-do, in progress, done) na Jiře. Budeme se scházet pravidelně každý týden na cviku a detailně diskutovat, případně o víkendu, pokud nastane správná konstelace hvězd a všichni se sejdeme.

- **Řízení úkolů na denní bázi:** 
Problémy budou řešeny operativně v rámci komentářů na Jiře, v případě větších problémů naplánujeme schůzku a pokud možno společně vyřešíme problém. V případě kritických problémů budeme muset vymyslet change request, který okamžitě implementujeme.


- **Správa verzí a integrace:** 
 Pro správu verzí budeme používat **Git**. V první fázi vývoje budeme veškeré inkrementy přidávat do main větve, jakmile se projekt dostane do fáze malého funkčního celku, budeme nové feature implementovat do větví. Integrace proběhne přes CI/CD pipeline, která zajistí průběžné testování.

## 4. Testování (Continuous Integration)

- **Průběžné testování během vývoje:** 
 Každý inkrement bude testován v rámci CI/CD procesu. Při každém merge do hlavní větve se spustí unit, integrační a akceptační testy.

- **Testy během sprintu:** 
 Každý sprint bude obsahovat unit testy (zahrnující klíčové funkce), integrační testy (např. propojení s databází PostgreSQL), a akceptační testy, které budou validovat, že splňujeme zadání user story.

- **Automatizace testů:** 
 Testovací proces bude automatizovaný pomocí nástroje GitHub Actions, čímž zajistíme rychlou zpětnou vazbu na změny. ( Možná Jenkins) 

## 5. Nasazení a retrospektiva

- **Plánování nasazení:** 
 Po každém sprintu se nový inkrement nasadí na staging prostředí pro zákazníka. Produkční nasazení proběhne po každém sprintu.

- **Monitoring produkce a řešení problémů:** 
 Produkční prostředí bude monitorováno (např. pomocí **Grafana**). Pokud dojde k chybě, bude zaveden rollback mechanismus k návratu k předchozí verzi. Logování a monitoring budou nastaveny tak, aby umožňovaly rychlé odhalení problému. V případě problému budeme rollback řešit Blue-Green deploymentem, kdy budeme zkoušet jestli verze připravená na nasazení je schopna v pořádku fungovat na produkčním prostředí, v případě problému jen vrátíme původní verzi zpět.

- **Retrospektiva:** 
 Po každém sprintu proběhne retrospektiva, sejdeme se a budem řešit naše úspěchy a nedostatky a způsob nápravy pro zlepšení. Na základě zpětné vazby se budou upravovat procesy pro další sprinty pro zvýšení efektivity a správnosti implementace každého sprintu.
