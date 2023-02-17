import os
import re
import json
import urllib3

# Forgive me for this jank, the documentation looks nice though.

# This generates documentation for the powers, origins, and conditions/actions

powerMarkdown = """# {name}
{desc}

{paramHeader}{field}

### Example
```json
{example}
```
{powerDesc}
"""
paramHeader = """
### Fields
Field | Type | Default | Description
------|------|---------|-------------
"""

originMarkdownTemplate = """# {name}
{desc}

| Name | ID | Description |
| ----- | ---- | ------------- |
"""
docFolders = ["docs", "docs/power_types", "docs/origins", "docs/conditions", "docs/conditions/entity",
              "docs/conditions/item", "docs/conditions/block", "docs/actions", "docs/actions/entity",
              "docs/conditions/damage", "docs/actions/bientity", "docs/actions/block", "docs/misc"]
for folder in docFolders:
    if not os.path.exists(folder):
        os.mkdir(folder)

# Lang File Reading, also getting the origins one, for powers used from origins
lang = json.load(open("src/main/resources/assets/moborigins/lang/en_us.json", "r"))
http = urllib3.PoolManager()
r = http.request('GET',
                 'https://raw.githubusercontent.com/apace100/origins-fabric/1.19/src/main/resources/assets/origins/lang/en_us.json')
originsLang = json.loads(r.data.decode('utf-8'))

# Origin Documentation
origins = []
for root, dirs, files in os.walk("src/main/resources/data/moborigins/origins"):
    for file in files:
        if file.endswith(".json"):
            with open(os.path.join(root, file), 'r') as f:
                origin = json.load(f)
                originName = lang["origin.moborigins." + file.split(".")[0] + ".name"]
                originDesc = lang["origin.moborigins." + file.split(".")[0] + ".description"]
                originMarkdown = originMarkdownTemplate.replace("{name}", originName).replace("{desc}", originDesc)
                origins.append("[" + originName + "](/origins/" + file.split('.')[0] + ")")
                for power in origin["powers"]:
                    powerInfo = power.split(":")
                    try:
                        if powerInfo[0] == "origins":
                            powerName = originsLang["power.origins." + powerInfo[1] + ".name"]
                            powerDesc = originsLang["power.origins." + powerInfo[1] + ".description"]
                        else:
                            powerName = lang["power." + powerInfo[0] + "." + powerInfo[1] + ".name"]
                            powerDesc = lang["power." + powerInfo[0] + "." + powerInfo[1] + ".description"]

                        originMarkdown += f"| {powerName} | `{power}` | {powerDesc} |\n"
                    except KeyError:
                        originMarkdown += f"| *hidden* | `{power}` | *No description available* |\n"

                with open(f"docs/origins/{file.split('.')[0]}.md", "w") as f:
                    f.write(originMarkdown)
with open(f"docs/misc/origins.md", "w") as f:
    markdown = "# Origins\n\n"
    for origin in origins:
        markdown += "- " + origin + "\n"
    f.write(markdown)


# Get all powers from the powers folder, and create a table listing them, and their description
powers = []
for root, dirs, files in os.walk("src/main/resources/data/moborigins/powers"):
    for file in files:
        if file.endswith(".json"):
            with open(os.path.join(root, file), 'r') as f:
                fileName = file.split(".")[0]
                try:
                    powerName = lang["power.moborigins." + fileName + ".name"]
                    powerDesc = lang["power.moborigins." + fileName + ".description"]
                except KeyError:
                    powerName = "*hidden*"
                    powerDesc = "*No description available*"

                powers.append(f"| {powerName} | `moborigins:{fileName}` | {powerDesc} |")

with open(f"docs/misc/powers.md", "w") as f:
    f.write("# Powers\n\n| Name | ID | Description |\n| ----- | ---- | ------------- |\n" + "\n".join(powers))


class DocumentationSource:
    def __init__(self, srcDir, destDir, sourceName):
        self.srcDir = srcDir
        self.destDir = destDir
        self.sourceName = sourceName
        self.fileNames = []

    def generate(self):
        for root, dirs, files in os.walk(self.srcDir):
            for file in files:
                if file.endswith(".java"):
                    with open(os.path.join(root, file), 'r') as f:
                        javaFile = f.read()
                        results = re.findall(r'\/\*\* {DOCS}[\s\S]+\*\/', javaFile)
                        if len(results) > 0:
                            # Check if {DOCS} has {IGNORE} in it, and if so, ignore this file
                            if "{IGNORE}" in results[0]:
                                continue
                            result = results[0].strip()
                            name = re.findall(r'NAME: .+', result)[0].replace("NAME: ", "")
                            desc = re.findall(r'(?<=DESC:)[\s\S]+(?=PARAMS:)', result)[0].replace("\n",
                                                                                                  "\n\n").strip()
                            params = re.findall(r'PARAMS[\s\S]+?EXAMPLE:', result)[0].replace("PARAMS:", "").replace(
                                "EXAMPLE:", "").strip().split("\n")
                            example = re.findall(r'EXAMPLE[\s\S]+?POWER_DESC:', result)[0].replace("EXAMPLE:",
                                                                                                   "").replace(
                                "POWER_DESC:", "").strip()
                            # # Saves the example to a file for testing
                            # with open(f"testdata/doc/powers/{name.lower().replace(' ', '_')}.json", "w") as f:
                            #     f.write(example)
                            powerDesc = re.findall(r'POWER_DESC: [\s\S]+ (?=\*\/)', result)
                            if len(powerDesc) > 0:
                                powerDesc = powerDesc[0].replace("POWER_DESC: ","").replace('\n','\n\n').strip()
                            else:
                                powerDesc = ""
                            paramMarkdown = ""
                            hasParams = False
                            for param in params:
                                if param != "":
                                    hasParams = True
                                    paramSplit = re.findall(r'(?<={)[^{}]+(?=})', param)
                                    # If param is empty don't italicize it
                                    if paramSplit[2].strip() == "":
                                        paramType = paramSplit[1]
                                    else:
                                        paramType = f"[{paramSplit[1]}]({paramSplit[2]})"
                                    if paramSplit[3].strip() == "":
                                        paramMarkdown += f"{paramSplit[0]} | {paramType} | {paramSplit[3]} | {paramSplit[4]}\n"
                                    else:
                                        paramMarkdown += f"{paramSplit[0]} | {paramType} | *{paramSplit[3]}* | {paramSplit[4]}\n"
                            markdown = powerMarkdown.replace("{name}", name).replace("{desc}", desc).replace(
                                "{example}",
                                example).replace(
                                "{powerDesc}", powerDesc).replace("{field}", paramMarkdown).replace("{paramHeader}",
                                                                                                    paramHeader if hasParams else "")
                            fileName = name.lower().replace(" ", "_")
                            self.fileNames.append(fileName)
                            with open(f"{self.destDir}/{fileName}.md", "w") as f:
                                f.write(markdown)
                        else:
                            print(f"Could not find docs for {file}")
        with open(f"docs/{self.sourceName}.md", "w") as f:
            listMarkdown = ""
            listMarkdown += f"# {self.sourceName.replace('_', ' ').title()}\n"
            self.fileNames.sort()
            for file in self.fileNames:
                listMarkdown += f"- [{file.replace('_', ' ').title()}]({self.destDir.replace('docs/', '')}/{file}.md)\n"
            f.write(listMarkdown)


# Documentation class for single files with multiple commented DOCS sections
class SingleFileDocumentationSource:
    def __init__(self, srcFile, destDir, sourceName):
        self.srcFile = srcFile
        self.destDir = destDir
        self.sourceName = sourceName
        self.fileNames = []

    def generate(self):
        with open(self.srcFile, 'r') as f:
            javaFile = f.read()
            result = re.findall(r'\/\*\* {DOCS}[\s\S]+?\*\/', javaFile)
            for r in result:
                name = re.findall(r'NAME: .+', r)[0].replace("NAME: ", "")
                desc = re.findall(r'(?<=DESC:)[\s\S]+(?=PARAMS:)', r)[0].replace("\n", "\n\n").strip()
                params = re.findall(r'PARAMS[\s\S]+?EXAMPLE:', r)[0].replace("PARAMS:", "").replace(
                    "EXAMPLE:", "").strip().split("\n")
                example = re.findall(r'EXAMPLE[\s\S]+?POWER_DESC:', r)[0].replace("EXAMPLE:", "").replace(
                    "POWER_DESC:", "").strip()
                powerDesc = re.findall(r'POWER_DESC: .+', r)[0].replace("POWER_DESC: ", "")
                paramMarkdown = ""
                hasParams = False
                for param in params:
                    if param != "":
                        hasParams = True
                        paramSplit = re.findall(r'(?<={)[^{}]+(?=})', param)
                        paramMarkdown += f"{paramSplit[0]} | [{paramSplit[1]}]({paramSplit[2]}) | *{paramSplit[3]}* | {paramSplit[4]}\n"
                markdown = powerMarkdown.replace("{name}", name).replace("{desc}", desc).replace("{example}",
                                                                                                 example).replace(
                    "{powerDesc}", powerDesc).replace("{field}", paramMarkdown).replace("{paramHeader}",
                                                                                        paramHeader if hasParams else "")
                fileName = name.lower().replace(" ", "_")
                self.fileNames.append(fileName)
                with open(f"{self.destDir}/{fileName}.md", "w") as f:
                    f.write(markdown)
        with open(f"docs/{self.sourceName}.md", "w") as f:
            entityActionsMarkdown = ""
            entityActionsMarkdown += f"# {self.sourceName.replace('_', ' ').title()}\n"
            for file in self.fileNames:
                entityActionsMarkdown += f"- [{file.replace('_', ' ').title()}]({self.destDir.replace('docs/', '')}/{file}.md)\n"
            f.write(entityActionsMarkdown)


PowerTypes = DocumentationSource("src/main/java/me/ultrusmods/moborigins/power", "docs/power_types", "power_types")
PowerTypes.generate()
EntityActions = DocumentationSource("src/main/java/me/ultrusmods/moborigins/action/entity", "docs/actions/entity",
                                    "entity_actions")
EntityActions.generate()
EntityConditions = DocumentationSource("src/main/java/me/ultrusmods/moborigins/condition/entity",
                                       "docs/conditions/entity", "entity_conditions")
EntityConditions.generate()
BiEntityActions = DocumentationSource("src/main/java/me/ultrusmods/moborigins/action/bientity", "docs/actions/bientity",
                                      "bi_entity_actions")
BiEntityActions.generate()

BlockActions = DocumentationSource("src/main/java/me/ultrusmods/moborigins/action/block", "docs/actions/block", "block_actions")
BlockActions.generate()

BlockConditions = SingleFileDocumentationSource(
    "src/main/java/me/ultrusmods/moborigins/condition/block/MobOriginsBlockConditions.java", "docs/conditions/block",
    "block_conditions")
BlockConditions.generate()
DamageConditions = SingleFileDocumentationSource(
    "src/main/java/me/ultrusmods/moborigins/condition/damage/MobOriginsDamageConditions.java", "docs/conditions/damage",
    "damage_conditions")
DamageConditions.generate()
ItemConditions = SingleFileDocumentationSource(
    "src/main/java/me/ultrusmods/moborigins/condition/item/MobOriginsItemConditions.java", "docs/conditions/item",
    "item_conditions")
ItemConditions.generate()


