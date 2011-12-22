#XDT4J - Xml Document Transform for Java

Provide an Java implementation of the Microsoft XML Document Transform (XDT) language that was included with Visual Studio 2010 for transforming XML configuration files.

This allow to modify, extend or clean an XML simply.

By example, binding this XML

	<Root>
		<Logging>
			<Logger name="myFirst" level="INFO"/>
		</Logging>
	</Root>
	
with this one :
	
	<Root xmlns:xdt="http://schemas.microsoft.com/XML-Document-Transform">
		<Logging>
			<Logger name="second" level="WARN" xdt:Transform="Insert" />
		</Logging>
	</Root>

generate this last :

	<Root>
		<Logging>
			<Logger name="myFirst" level="INFO"/>
			<Logger name="second" level="WARN" />
			</Logging>
		</Root>


##Building

    mvn clean install


##Available transformations

### Insert

### InsertBefore

### InsertAfter

### Remove

### RemoveAll

### SetAttribute

### RemoveAttribute

### Replace





