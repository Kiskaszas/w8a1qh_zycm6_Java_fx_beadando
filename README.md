# w8a1qh_zycm6_Java_fx_beadando

# FXTrader & Data Manager

## **Projekt Leírás**
A **FXTrader & Data Manager** egy JavaFX alapú asztali alkalmazás, amely különböző adatforrásokat kezel, beleértve az SQLite adatbázist, a Magyar Nemzeti Bank SOAP API-ját és az OANDA Forex API-ját. Az alkalmazás célja, hogy bemutassa az adatbázis-kezelés, API-integráció és a párhuzamos programvégrehajtás lehetőségeit.

---

## **Funkciók**
- **Adatbázis kezelés (CRUD műveletek)** – Egy SQLite alapú adatbázis-kezelő rendszer, amely támogatja az adatok olvasását, szűrését, írását, módosítását és törlését.
- **SOAP kliens (MNB API)** – A Magyar Nemzeti Bank árfolyamadataihoz történő hozzáférés és letöltés fájlba.
- **Forex API integráció (OANDA)** – Devizaárfolyamok lekérdezése és grafikonon való megjelenítése.
- **Párhuzamos programvégrehajtás** – Több szálas működés demonstrációja JavaFX környezetben.
- **GitHub integráció** – Verziókövetés és projektmunka támogatása.

---

## **Telepítés és futtatás**
### **1. Szükséges eszközök**
- Java 17 vagy újabb
- JavaFX 17 SDK
- Maven (opcionális)

### **2. A projekt letöltése és beállítása**
```bash
git clone https://github.com/your-repo/fxtrader-data-manager.git
cd fxtrader-data-manager
```

### **3. Az alkalmazás futtatása**
```bash
mvn clean install
java -jar target/fxtrader-data-manager.jar
```
Vagy futtathatod közvetlenül az IDE-ből a `App.java` osztályból.

---

## **Használat**
1. **Indítsd el az alkalmazást**
2. **Válaszd ki az adatbázis kezelési funkciókat** (CRUD műveletek)
3. **Tölts le adatokat az MNB API-ról** és elemezd őket grafikonokon
4. **Kérdezd le az aktuális devizaárakat** az OANDA API segítségével
5. **Próbáld ki a párhuzamos programvégrehajtási példát**

---

## **Fejlesztők**
- **Kaszás Viktor** – Backend fejlesztés és Frontend és API integráció

## **GitHub verziókezelés**
A projekt munkafolyamata a GitHub-on történik. Minden csapattag saját ágon dolgozik, és legalább 5 commit-ot kell végrehajtania.

**Repo URL:**
https://github.com/Kiskaszas/w8a1qh_zycm6_Java_fx_beadando/

## **Licenc**
MIT License

