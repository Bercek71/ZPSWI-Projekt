# Procesní postupy pro Agile vývoj - Hotelový rezervační systém

> Toto je vyplivnuto AI - musí se upravit.

## 1. Sběr a správa požadavků (Product Backlog)

- **Správa backlogu požadavků:**  
  Požadavky od zákazníka budou přidány do backlogu v nástroji, jako je Jira nebo Trello. Každý požadavek bude popsán formou user story s jasnými kritérii přijetí. Pravidelně budeme kontrolovat, zda jsou všechny položky aktuální, a podle potřeby přidáme nové informace nebo upravíme stávající položky.

- **Aktualizace a změny v požadavcích:**  
  Aktualizace backlogu budou prováděny po každé interakci se zákazníkem nebo po každé retrospektivě. Pro změny bude zaveden mechanismus change requestů, které projdou schválením před začleněním do backlogu.

- **Komunikace o prioritách:**  
  Pravidelné schůzky (např. jednou týdně) se zákazníkem budou určeny pro diskuzi priorit user stories. Na těchto schůzkách se prioritizují stories podle business hodnoty a technických omezení.

- **Revize backlogu:**  
  Backlog bude revidován minimálně před každým sprintem. Navíc bude po každém sprintu zhodnocen a upraven podle zpětné vazby od týmu a zákazníka.

- **Definition of Done (DoD):**  
  DoD bude definováno pro každý inkrement na začátku sprintu. Typicky bude zahrnovat splnění kritérií přijetí, testování (unit, integrační testy), kódovou recenzi a dokumentaci.

## 2. Plánování sprintů a Product Increments

- **Plánování sprintů:**  
  Sprinty budou plánovány na začátku každého cyklu (dvoutýdenního sprintu). Práce bude rozdělena podle zkušeností a dostupnosti členů týmu s ohledem na rovnoměrné zatížení.

- **Určování user stories pro sprint:**  
  Pomocí techniky odhadování (např. **story points**, **Planning Poker**) určíme, které user stories jsou reálné dokončit během sprintu. Prioritizace bude založena na dohodě se zákazníkem.

- **Zajištění funkčních výstupů:**  
  Na konci každého sprintu bude každý inkrement plně funkční, otestovaný a připravený k integraci. Použijeme CI/CD pipeline k automatickému nasazování a testování výstupů.

- **Sprint review a retrospektivy:**  
  Na konci každého sprintu proběhne sprint review s prezentací výsledků zákazníkovi a retrospektiva pro tým, kde se bude hodnotit, co fungovalo a co je potřeba zlepšit.

## 3. Implementace (Iterace vývoje)

- **Sledování průběhu sprintů:**  
  Pro sledování pokroku budeme používat Kanban board, který bude zobrazovat stav jednotlivých user stories (to-do, in progress, done). Na denní bázi budou probíhat daily stand-upy.

- **Řízení úkolů na denní bázi:**  
  Během daily stand-upů (max 15 minut) bude každý člen týmu hlásit pokrok, překážky a plány na další den. Problémy budou řešeny okamžitě nebo na dedikovaných schůzkách po stand-upu.

- **Správa verzí a integrace:**  
  Pro správu verzí budeme používat **Git**, kde bude každá feature branch sloučitelná s hlavní větví (použití pull requestů). Integrace proběhne přes CI/CD pipeline, která zajistí průběžné testování a nasazení.

## 4. Testování (Continuous Integration)

- **Průběžné testování během vývoje:**  
  Každý inkrement bude testován v rámci CI/CD procesu. Při každém merge do hlavní větve se spustí unit, integrační a akceptační testy.

- **Testy během sprintu:**  
  Každý sprint bude obsahovat unit testy (zahrnující klíčové funkce), integrační testy (např. propojení s databází PostgreSQL), a akceptační testy, které budou validovat, že splňujeme zadání od zákazníka.

- **Automatizace testů:**  
  Testovací proces bude automatizovaný pomocí nástrojů jako **Jenkins** a **Selenium** pro frontend testy, čímž zajistíme rychlou zpětnou vazbu na změny.

## 5. Nasazení a retrospektiva

- **Plánování nasazení:**  
  Po každém sprintu se nový inkrement nasadí na staging prostředí pro zákazníka. Produkční nasazení proběhne buď po každém sprintu, nebo při dosažení milníku.

- **Monitoring produkce a řešení problémů:**  
  Produkční prostředí bude monitorováno (např. pomocí **Grafana**). Pokud dojde k chybě, bude zaveden rollback mechanismus k návratu k předchozí verzi. Logování a monitoring budou nastaveny tak, aby umožňovaly rychlé odhalení problému.

- **Retrospektiva:**  
  Po každém sprintu proběhne retrospektiva, kde se tým zaměří na úspěchy a oblasti pro zlepšení. Na základě zpětné vazby se budou upravovat procesy pro další sprinty.
