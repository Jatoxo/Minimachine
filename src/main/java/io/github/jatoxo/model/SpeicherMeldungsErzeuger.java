//


package io.github.jatoxo.model;

interface SpeicherMeldungsErzeuger {
	void Registrieren(MemoryListener var1);

	void Abmelden(MemoryListener var1);
}
