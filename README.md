# Laboratorium 3, TN 11:00

Podczas laboratorium należy zbudować aplikację pozwalającą na interakcje z użytkownikiem z poziomu konsoli oraz umożliwiającej wykonywanie operacji CRUD (od ang. create, read, update and delete; pol. utwórz, odczytaj, aktualizuj i usuń) na danych, które są w jakiś sposób utrwalane (zapisywane w plikach).
Wymagane jest, by logika biznesowa rozwiązania została oddzielona od interfejsu użytkownika (by dało się bez problemu podmienić interfejs tekstowy na interfejs graficzny). Ponadto należy zaprojektować odpowiednie struktury danych (do czego można wykorzystać kolekcje i mapy) oraz obsłużyć wyjątki (pojawiające się podczas operacji na strumieniach, opcjonalnie - pojawiające się podczas operacji na strukturach danych).
Aplikacji wspierać ma działanie firmy zajmującej się wykonywaniem prac ogrodniczych na zlecenie. Zakładamy, że z aplikacji tej będą korzystać: menadżer, pracownik, klient.
Klient: ma możliwość przeglądania ofert firmy, ma możliwość składania zamówienia, otrzymuje informację o terminie realizacji zamówienia, ma możliwość przeglądania swoich zamówień.
Pracownik: ma dostęp do terminarza zleceń, realizuje zlecenie
Menadżer: przegląda zamówienia i generuje zlecenia, wystawia rachunki po wykonaniu zlecenia
Zakładamy, że aplikacja udostępniać będzie trzy interfejsy użytkownika: dla klienta, dla pracownika, dla menadżera. Niech to będą interfejsy uruchamiane w osobnych metodach main różnych klas. Czyli niech to będą osobne programy oferujące funkcje jak niżej, współdzielące utrwalane gdzieś dane. Wymienione funkcje to funkcje ogólne. W ich skład mogą wchodzić funkcje szczegółowe, jak np. wyszukiwanie, filtrowanie, sortowanie.

* Program "Interfejs klienta"
  * przeglądanie bieżącej oferty;
  * tworzenie zamówienia (zamówione usługi trafiają do koszyka);
  * składanie zamówienia;
  * przeglądanie stanu zamówienia;
  * rozliczenie zamówienia (dokonanie "płatności").
* Program "Interfejs pracownika"
  * przeglądanie terminarza zleceń;
  * zmiana statusu zlecenia (co wiązać się ma z pobraniem zlecenia oraz wykonaniem zlecenia).
* Program "Interfejs menadżera"
  * redakcja oferty;
  * akceptacja zamówień (by klient widział, że zamówienie przyjęto do realizacji);
  * tworzenie zleceń dla pracowników (odpowiadających zamówieniom);
  * wystawianie rachunku za wykonane zlecenia.

Zakładamy, że aplikacja będzie komunikować się z użytkownikami z poziomu konsoli (czyli, że interfejs użytkownika będzie tekstowy - jeśli ktoś chciałby zaimplementować interfejs graficzny, to oczywiście może to zrobić, jednak nie jest to wymagane). Synchronizacja danych pomiędzy poszczególnymi częściami aplikacji powinna odbywać się poprzez pliki (można zastosować strumienie obiektów). Aplikacja ma działać w trybie uproszczonym (realizacja "płatności" przez klienta to po prostu odpowiednia zmiana danych) Model danych może być uproszczony. Wystarczy, że będzie on uwzględniał:

* dane dotyczące oferty: identyfikator usługi, nazwa usługi, jednostka, cena (np.: "sadzenie drzewa", "1 sztuka", "30 PLN" lub "koszenie trawnika", "1 m2", "2 PLN");
* dane dotyczące zamówienia: identyfikator zamówienia, data zamówienia, zamawiający, lista zamówionych usług (lista par: identyfikator usługi, liczba jednostek), status zamówienia;
* dane dotyczące zlecenia: identyfikator zlecenia, identyfikator zamówienia, identyfikator pracownika;
* dane dotyczące klientów: identyfikator klient, nazwa klienta;
* dane dotyczące pracowników: identyfikator pracownika, nazwa pracownika.

Proszę zastanowić się nad tym, jak będą wyglądały struktury danych przechowywane w pamięci i jak będzie wyglądał ich zapis w plikach.
Pozostałe szczegóły mają być zgodne z ustaleniami poczynionymi na początku zajęć.
