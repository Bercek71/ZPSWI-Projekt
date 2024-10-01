# Zákaznické zadání: Vývoj e-commerce platformy

**1. Popis projektu:**
Vytvoření e-commerce platformy, která umožní uživatelům nakupovat produkty online. Platforma bude zahrnovat správu produktů jako je dostupnost, funkci nákupního košíku, platební bránu a systém hodnocení a recenzí produktů. Cílem je nabídnout uživatelsky přívětivé a bezpečné řešení pro obchodníky i zákazníky.

**2. Funkce systému:**

- **Správa produktů:**
  - Možnost přidávat, upravovat a mazat produkty.
  - Kategorizace produktů (např. podle typu, značky, ceny, dostupnosti).
  - Možnost nahrávání fotografií, přidání popisků, specifikací a cen.
  - Sledování skladové dostupnosti.

- **Košík a objednávkový proces:**
  - Možnost přidávat produkty do košíku.
  - Uživatelé mohou upravovat množství produktů v košíku.
  - Zobrazení celkové ceny včetně DPH a případného poštovného.
  - Přehled objednávky před jejím dokončením.
  - Systém pro správu adresy doručení a kontaktních údajů.

- **Platby:**
  - Integrace platebních metod (kreditní karty, PayPal, bankovní převody).
  - Bezpečné zpracování plateb (podpora šifrování a zabezpečení).
  - Potvrzení úspěšné transakce a odeslání potvrzení emailem.

- **Recenze a hodnocení:**
  - Uživatelé mohou po nákupu zanechat hodnocení produktu.
  - Systém pro zadávání recenzí s textovým polem a hodnocením (1–5 hvězdiček).
  - Moderace recenzí pro zamezení nevhodného obsahu.

**3. Uživatelské role:**

- **Zákazník:**
  - Registrace a správa uživatelského účtu (osobní údaje, historie objednávek).
  - Možnost procházet produkty, přidávat je do košíku a provést platbu.
  - Přístup k recenzím a hodnocením produktů.

- **Administrátor (obchodník):**
  - Přístup k administrativnímu rozhraní pro správu produktů, objednávek a uživatelů.
  - Sledování statistik prodeje a stavu skladu (Sledování objednaného zboží od dodavatele, sledování objednávek zákazníků - Co je objednané, kolik, zaplaceno? / dobírka?)
  - Správa kategorií produktů a zpracování objednávek.
  - Možnost zrušit objednávku

**4. Technologie:**
  - Frontend: Moderní framework pro zajištění interaktivního uživatelského rozhraní.
  - Backend: Vhodná technologie pro zpracování požadavků a komunikaci s databází
  - Databáze: MySQL, PostgreSQL nebo MongoDB pro správu produktů, uživatelských účtů a objednávek.
  - Integrace platební brány: Stripe, PayPal nebo jiná bezpečná platební platforma.
  - Bezpečnost: Implementace HTTPS, šifrování platebních údajů, ochrana proti útokům.

**5. Cíle a očekávání:**
  - Vysoce dostupný a responzivní systém, který zvládne velký počet uživatelů.
  - Bezpečný a jednoduchý nákupní proces.
  - Možnost rozšiřování o nové funkce (např. slevové kódy, věrnostní programy).

**6. Speciální požadavky:**
  - Responzivní design pro mobilní zařízení.
  - Možnost překladu do více jazyků.
  - Notifikace při dostupnosti zboží
  - Newsletter
  - Škálovatelnost systému