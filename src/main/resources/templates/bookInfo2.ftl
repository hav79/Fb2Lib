<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Book info</title>
</head>
<body>
<h2>Title: ${title}</h2>
<p><i>Original Title: ${src_title}</i></p>
<p>File: ${filename}</p>
<p>Authors:</p>
<ul>
    <#list authors as author>
        <li>${author.firstName} ${author.middleName} ${author.lastName} (${author.srcFirstName} ${author.srcMiddleName}
            ${author.srcLastName})
        </li>
    </#list>
</ul>
<p>Year: ${year}</p>
<p>Language: ${lang} (${src_lang})</p>
<p>Genres:</p>
<p>
    <#list genres as genre>${genre}
        <#sep>,</#sep>
    </#list>
</p>

<#if sequences??>
<p>Sequences:</p>
<ul>
    <#list sequences as sequence>
        <li>${sequence.sequence.name} (${sequence.sequence.srcName}) - ${sequence.number}</li>
    </#list>
</ul>
</#if>

<h3>Document:</h3>
<p>ID: ${doc_id}</p>
<p>Version: ${version}</p>
<p>Program used: ${program_used}</p>
<#if edition??>
<h3>Edition:</h3>
<p>Name: ${edition.bookName}</p>
<p>${edition.publisher} (${edition.year}, ${edition.city})</p>
<p>ISBN: ${edition.isbn}</p>
<p>Sequence: ${edition.sequenceName}
    <#if edition.sequenceNumber != 0> ${edition.sequenceNumber}</#if>
</p>
</#if>
</body>
</html>