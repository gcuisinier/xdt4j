#XDT4J - Xml Document Transform for Java

Provide an Java implementation of the Microsoft XML Document Transform (XDT) language that was included with Visual Studio 2010 for transforming XML configuration files.
It is inspired from the work of the Opensource C# implementation (http://code.google.com/p/xdt/)

This allow to modify, extend or clean an XML simply.

By example, binding this XML
```xml
<Root>
	<Logging>
		<Logger name="myFirst" level="INFO"/>
		<Logger name="mySecond" level="WARN"/>
		<Logger name="myThird" level="INFO"/>
	</Logging>
</Root>
```
with this one :

```xml
	<Root xmlns:xdt="http://schemas.microsoft.com/XML-Document-Transform">
		<Logging>
			<Logger name="myFourth" level="WARN" xdt:Transform="Insert" />
		</Logging>
	</Root>
```

generate this last :

```xml
<Root>
	<Logging>
        <Logger name="myFirst" level="INFO"/>
		<Logger name="mySecond" level="WARN"/>
		<Logger name="myThird" level="INFO"/>
		<Logger name="myFourth" level="WARN" />
	</Logging>
</Root>
```

##Building

    mvn clean install

##Using

The current release is already synced to Maven Central, you can simply add a depencendy to this one :

```xml
<dependency>
    <groupId>be.hikage</groupId>
    <artifactId>xdt4j</artifactId>
    <version>1.0</version>
</dependency>
```

To use lasted snapshot, you have to use the OSS Sonatype repository :

##Available transformations

### Insert

Adds the element that is defined in the transform file as a sibling to the selected element or elements. The new element is added at the end of any collection.

Example :
```xml
<Root xmlns:xdt="http://schemas.microsoft.com/XML-Document-Transform">
	<Logging>
		<Logger name="myFourth" level="WARN" xdt:Transform="Insert" />
	</Logging>
</Root>
```

### InsertBefore

Inserts the element that is defined in the transform XML directly before the element that is selected by the specified XPath expression.

```xml
<Root xmlns:xdt="http://schemas.microsoft.com/XML-Document-Transform">
	<Logging>
		<Logger name="My1.5" level="WARN" xdt:Transform="InsertBefore(/Root/Logging/Logger[@name='MySecond']" />
	</Logging>
</Root>
```

Result :

```xml
<Root>
	<Logging>
		<Logger name="myFirst" level="INFO"/>
		<Logger name="my1.5" level="WARN" />
		<Logger name="mySecond" level="WARN"/>
		<Logger name="myThird" level="INFO"/>
	</Logging>
</Root>
```


### InsertAfter

Inserts the element that is defined in the transform XML directly after the element that is selected by the specified XPath expression.

```xml
<Root xmlns:xdt="http://schemas.microsoft.com/XML-Document-Transform">
	<Logging>
		<Logger name="My1.5" level="WARN" xdt:Transform="InsertAfter(/Root/Logging/Logger[@name='MyFirst']" />
	</Logging>
</Root>
```

Result :

```xml
<Root>
	<Logging>
		<Logger name="myFirst" level="INFO"/>
		<Logger name="my1.5" level="WARN" />
		<Logger name="mySecond" level="WARN"/>
		<Logger name="myThird" level="INFO"/>
	</Logging>
</Root>
```

### Remove

Removes the selected element. If multiple elements are selected, removes the first element.

```xml
<Root xmlns:xdt="http://schemas.microsoft.com/XML-Document-Transform">
	<Logging>
		<Logger xdt:Transform="Remove" />
	</Logging>
</Root>
```

Result :

```xml
<Root>
	<Logging>
		<Logger name="mySecond" level="WARN"/>
		<Logger name="myThird" level="INFO"/>
	</Logging>
</Root>
```

### RemoveAll

Removes the selected element or elements.

```xml
<Root xmlns:xdt="http://schemas.microsoft.com/XML-Document-Transform">
	<Logging>
		<Logger xdt:Transform="Remove" />
	</Logging>
</Root>
```

Result :

```xml
<Root>
	<Logging>
	</Logging>
</Root>
```


### SetAttribute

Sets attributes for selected elements to the specified values. The Replace transform attribute replaces an entire element including all of its attributes. In contrast, the SetAttributes attribute enables you to leave the element as it is but change selected attributes. If you do not specify which attributes to change, all of the attributes that are present in the element in the transform file are changed.

The SetAttributes transform affects all selected elements. This is different from the Replace transform attribute, which affects only the first selected element if multiple elements are selected.

### RemoveAttribute

Removes specified attributes from the selected elements.
If you do not specify which attributes to change, all of the attributes present are removed

Example 1 :

```xml
<Root xmlns:xdt="http://schemas.microsoft.com/XML-Document-Transform">
	<Logging>
		<Logger xdt:Transform="Remove" />
	</Logging>
</Root>
```

Result :

```xml
<Root>
	<Logging>
    	<Logger />
		<Logger />
		<Logger />
	</Logging>
</Root>
```


But you can specify which attribute to remove :

```xml
<Root xmlns:xdt="http://schemas.microsoft.com/XML-Document-Transform">
	<Logging>
		<Logger xdt:Transform="Remove(level)" />
	</Logging>
</Root>
```

Result :

```xml
<Root>
	<Logging>
    	<Logger name="myFirst" />
    	<Logger name="mySecond" />
    	<Logger name="myThird" />
    </Logging>
</Root>
```

### Replace

Replaces the selected element with the element that is specified in the transform file. If more than one element is selected, only the first selected element is replaced. For an example of how to use the Replace keyword, see the examples for the Locator attributes.

```xml
<Root xmlns:xdt="http://schemas.microsoft.com/XML-Document-Transform">
	<Logging  xdt:Transform="Replace">
		<Logger name="myOtherFirst" />
		<Logger name="myOtherSecond" />
    	<Logger name="myOtherThird" />
    </Logging>
</Root>
```

Result :

```xml
<Root>
    <Logging>
		<Logger name="myOtherFirst" />
		<Logger name="myOtherSecond" />
		<Logger name="myOtherThird" />
	</Logging>
</Root>
```



