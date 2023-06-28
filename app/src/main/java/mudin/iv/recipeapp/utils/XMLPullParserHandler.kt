package mudin.iv.recipeapp.utils

import android.util.Log
import android.util.Xml
import mudin.iv.recipeapp.model.RecipeType
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import java.util.*

/*
* Copyright (C) 2012 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
* in compliance with the License. You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software distributed under the License
* is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
* or implied. See the License for the specific language governing permissions and limitations under
* the License.
*/


//gain from official example at Android Studio
class XMLPullParserHandler
{
    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(`in`: InputStream): List<RecipeType> {
        return try {
            val parser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(`in`, null)
            parser.nextTag()
            readRecipeTypes(parser)
        } finally {
            `in`.close()
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readRecipeTypes(parser: XmlPullParser): List<RecipeType> {
        val recipetypes: MutableList<RecipeType> = ArrayList<RecipeType>()
        parser.require(XmlPullParser.START_TAG, ns, "recipetypes")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            val name = parser.name
            // Starts by looking for the entry tag
            if (name == "recipetype") {
                recipetypes.add(readRecipeType(parser))
            } else {
                skip(parser)
            }
        }
        return recipetypes
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readRecipeType(parser: XmlPullParser): RecipeType {
        parser.require(XmlPullParser.START_TAG, ns, "recipetype")
        val id = parser.getAttributeValue(null, "id")
        var name: String = ""
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            val parserName = parser.name
            if (parserName == "name") {
                name = readName(parser)
            } else {
                skip(parser)
            }
        }
        //just want to check whether its running okay or not
        Log.d("TestOutputXML", "$id $name")
        return RecipeType(id, name)
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readName(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "name")
        val name = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "name")
        return name
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    // Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
    // if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
    // finds the matching END_TAG (as indicated by the value of "depth" being 0).
    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        check(parser.eventType == XmlPullParser.START_TAG)
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }

    companion object {
        private val ns: String? = null
    }
}