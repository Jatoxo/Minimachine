//


package io.github.jatoxo.model.minilanguage;

abstract class Attribut {
	Attribut() {
	}

	abstract void Laden(AssemblerText var1);

	abstract void Operation(AssemblerText var1, String var2);
}
