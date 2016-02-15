<html>
    <h1>File: ${filename}</h1>
    Title: ${title}
    Original Title: ${src_title}
    Authors:
        <#list authors as author>
            ${author.firstName} ${author.middleName} ${author.lastName} (${author.srcFirstName} ${author.srcMiddleName} ${author.srcLastName})
        </#list>
    Year: ${year}
    Language: ${lang} (${src_lang})
    Genres:
    <#list genres as genre>${genre}<#sep>, </#sep></#list>

    Document:
    ID: ${doc_id}
    Version: ${version}
    Program used: ${program_used}
    <#if edition != null>
    Edition:
    Name: ${edition.bookName}
          ${edition.publisher} (${edition.year}, ${edition.city})
    ISBN: ${edition.isbnSet}
    Sequence: ${edition.sequenceName} <#if edition.sequenceNumber != 0> ${edition.sequenceNumber} </#if>
    </#if>
</html>