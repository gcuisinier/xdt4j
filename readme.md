#XDT4J - Xml Document Transform for Java

Provide an Java implementation of the Microsoft XML Document Transform (XDT) language that was included with Visual Studio 2010 for transforming XML configuration files.
It is inspired from the work of the Opensource C# implementation (http://code.google.com/p/xdt/)

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

Adds the element that is defined in the transform file as a sibling to the selected element or elements. The new element is added at the end of any collection.


### InsertBefore

Inserts the element that is defined in the transform XML directly before the element that is selected by the specified XPath expression. The XPath expression must be an absolute expression, because it is applied to the development Web.config file as a whole; it is not appended only to the current element's implicit XPath expression.

### InsertAfter

Inserts the element that is defined in the transform XML directly after the element that is selected by the specified XPath expression. The XPath expression must be an absolute expression, because it is applied to the development Web.config file as a whole; it is not appended to the current element's implicit XPath expression.

### Remove

Removes the selected element. If multiple elements are selected, removes the first element.

### RemoveAll

Removes the selected element or elements.


### SetAttribute

Sets attributes for selected elements to the specified values. The Replace transform attribute replaces an entire element including all of its attributes. In contrast, the SetAttributes attribute enables you to leave the element as it is but change selected attributes. If you do not specify which attributes to change, all of the attributes that are present in the element in the transform file are changed.

The SetAttributes transform affects all selected elements. This is different from the Replace transform attribute, which affects only the first selected element if multiple elements are selected.

### RemoveAttribute

Removes specified attributes from the selected elements.
If you do not specify which attributes to change, all of the attributes present are removed

### Replace

Replaces the selected element with the element that is specified in the transform file. If more than one element is selected, only the first selected element is replaced. For an example of how to use the Replace keyword, see the examples for the Locator attributes.




