package be.hikage.xdt4j.locator;

import com.google.common.base.Joiner;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class MatchLocator extends Locator {


    public MatchLocator(String parameter) {
        super(parameter);
    }

    @Override
    public String generateXPath(Element target) {
        List<String> conditionsList = new ArrayList<String>();
        StringBuilder resultXpath = new StringBuilder(target.getPath());


        if (parameter != null && !parameter.isEmpty()) {
            resultXpath.append("[");
            String[] tokens = parameter.split(",");
            for (String attributeName : tokens) {
                Attribute attr = target.attribute(attributeName);
                StringBuilder builder = new StringBuilder();
                builder.append("@").append(attr.getName()).append("=").append("\"").append(attr.getValue()).append("\"");
                conditionsList.add(builder.toString());

            }
            Joiner.on(" and ").appendTo(resultXpath, conditionsList);
            resultXpath.append("]");
        }


        return resultXpath.toString();

    }


}
