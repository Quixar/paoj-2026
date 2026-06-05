# Catalog Școlar

## 1. Definirea sistemului

Aplicația modelează un catalog școlar digital, unde pot fi gestionați elevi, profesori, materii, note, absențe și grupe/clase.

## 1.1 Acțiuni / interogări posibile în sistem

1. Adaugă un student nou în catalog.
2. Șterge un student din catalog.
3. Caută un student după codul de înmatriculare.
4. Listează toți studenții.
5. Adaugă o materie nouă.
6. Șterge o materie.
7. Caută o materie după cod.
8. Listează toate materiile.
9. Adaugă o notă unui student la o anumită materie.
10. Adaugă o absență unui student la o anumită materie.
11. Afișează notele unui student.
12. Afișează absențele unui student.
13. Calculează media unui student.
14. Listează studenții sortați alfabetic.
15. Adaugă un student într-o grupă.

## 1.2 Tipuri de obiecte din domeniu

1. `Person` — persoană abstractă din sistem.
2. `Student` — elev/student înscris în catalog.
3. `Profesor` — profesor care predă una sau mai multe materii.
4. `Subject` — materie școlară.
5. `Grade` — notă primită de un student.
6. `Absence` — absență înregistrată pentru un student.
7. `Group` — grupă/clasă de studenți.
8. `CodInmatriculare` — cod unic și imutabil al unui student.

## 2. Implementare Java

Sistemul folosește principii OOP:

- încapsulare prin atribute private/protected;
- moștenire: `Person -> Student`, `Person -> Profesor`;
- clasă abstractă: `Person`;
- clasă imutabilă: `CodInmatriculare`;
- suprascriere `toString`, `equals`, `hashCode`;
- colecții `List`, `Set`, `Map`, `TreeSet`;
- servicii Singleton:
    - `StudentService`
    - `SubjectService`
    - `CatalogService`;
- excepții custom:
    - `StudentNotFoundException`
    - `SubjectNotFoundException`.