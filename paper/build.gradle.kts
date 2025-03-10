plugins {
	`java-library`
	id("net.civmc.civgradle")
	id("io.papermc.paperweight.userdev") //version "1.3.1"
	id("com.github.johnrengelman.shadow")
}

civGradle {
	paper {
		pluginName = "RailSwitch"
	}
}

dependencies {
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")

    compileOnly("net.civmc.civmodcore:paper:2.0.0-SNAPSHOT:dev-all")
    compileOnly("net.civmc.namelayer:paper:3.0.0-SNAPSHOT:dev")
    compileOnly("net.civmc.citadel:paper:5.0.0-SNAPSHOT:dev")

	implementation("com.google.re2j:re2j:1.7")
}
