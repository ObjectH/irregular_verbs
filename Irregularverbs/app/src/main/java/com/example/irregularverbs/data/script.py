import codecs
with codecs.open("DBHelper.java", encoding="utf-8") as f:
    data = f.read()
    data = data.replace('\\"', "")
    data = data.replace("sqLiteDatabase.execSQL(\"INSERT INTO IRREGURAL_VERBS (FORM_1, FORM_2, FORM_3, TRANSLATION, LINK) VALUES (", "")
    data = data.replace("        ", "")
    data = data.split("\n")
    data = data[26:320]
    for d in range(0, len(data)):
        data[d] = data[d].split(", ")
        data[d] = data[d][0:4]
print(data)
