//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model;

import res.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

class Parser {
	private Scanner scanner;
	private Speicher speicher;
	private FehlerVerwaltung fehler;
	private AssemblerBefehle befehle;
	private int aktToken;
	private int pc;
	private HashMap<String, Integer> marken;
	private HashMap<Integer, String> fixierungen;
	private boolean erweitert;

	Parser(Scanner scanner, Speicher memory, FehlerVerwaltung exceptionHandler, boolean extended) {
		this.scanner = scanner;
		this.speicher = memory;
		this.fehler = exceptionHandler;
		this.erweitert = extended;
		this.pc = 0;
		this.befehle = AssemblerBefehle.AssemblerbefehleGeben();
		this.marken = new HashMap(40);
		this.fixierungen = new HashMap(80);
		this.aktToken = this.scanner.NächstesToken();
	}

	void Parse() {
		while(this.aktToken != 0) {
			while(this.aktToken == 5) {
				this.aktToken = this.scanner.NächstesToken();
			}

			Scanner var10001 = this.scanner;
			if (this.aktToken != 2) {
				if (this.aktToken != 0) {
					this.fehler.FehlerEintragen(R.getResources().getString("parse_error_expected_label_name"), this.scanner.PositionGeben());
					this.Überspringen();
				}
			} else {
				String label = this.scanner.BezeichnerGeben();
				int var6 = this.scanner.PositionGeben();
				this.aktToken = this.scanner.NächstesToken();
				var10001 = this.scanner;
				if (this.aktToken == 4) {
					if (this.marken.containsKey(label)) {
						this.fehler.FehlerEintragen(R.getResources().getString("parse_error_label_exists"), this.scanner.PositionGeben());
					} else if (!this.erweitert || !label.equals("SP") && !label.equals("sp")) {
						this.marken.put(label, this.pc);
					} else {
						this.fehler.FehlerEintragen(R.getResources().getString("parse_error_invalid_label") + ": " + label, this.scanner.PositionGeben());
					}

					this.aktToken = this.scanner.NächstesToken();
					var10001 = this.scanner;
					if (this.aktToken == 2) {
						label = this.scanner.BezeichnerGeben();
						var6 = this.scanner.PositionGeben();
						this.aktToken = this.scanner.NächstesToken();
					} else {
						var10001 = this.scanner;
						if (this.aktToken == 0) {
							continue;
						}

						var10001 = this.scanner;
						if (this.aktToken == 5) {
							continue;
						}

						this.fehler.FehlerEintragen(R.getResources().getString("parse_error_expected_label_name"), this.scanner.PositionGeben());
					}
				}

				if (!this.befehle.BezeichnerTesten(label)) {
					this.fehler.FehlerEintragen(R.getResources().getString("parse_error_invalid_instruction") + ": " + label, var6);
					this.Überspringen();
				} else {
					int var2 = this.befehle.OpcodeGeben(label);
					byte var3 = 0;
					byte var5;
					if (var2 < 0) {
						var5 = 1;
						if (this.aktToken == 7) {
							var5 = -1;
							this.aktToken = this.scanner.NächstesToken();
						} else if (this.aktToken == 6) {
							this.aktToken = this.scanner.NächstesToken();
						}

						if (this.aktToken == 3) {
							this.speicher.WortSetzen(this.pc, var5 * this.scanner.ZahlGeben());
							++this.pc;
							this.aktToken = this.scanner.NächstesToken();
						} else {
							this.speicher.WortSetzen(this.pc, 0);
							++this.pc;
							this.fehler.FehlerEintragen(R.getResources().getString("parse_error_expected_number"), this.scanner.PositionGeben());
						}
					} else {
						int var4;
						var4 = 0;

						if (var2 >= 300) {
							var3 = 2;
							var2 -= 300;
							var5 = 1;
							var10001 = this.scanner;
							if (this.aktToken == 2) {
								label = this.scanner.BezeichnerGeben();
								if (this.marken.containsKey(label)) {
									var4 = (Integer)this.marken.get(label);
								} else {
									this.fixierungen.put(this.pc + 1, label);
								}

								this.aktToken = this.scanner.NächstesToken();
							} else {
								var10001 = this.scanner;
								if (this.aktToken == 7) {
									var5 = -1;
									this.aktToken = this.scanner.NächstesToken();
								} else {
									var10001 = this.scanner;
									if (this.aktToken == 6) {
										this.aktToken = this.scanner.NächstesToken();
									}
								}

								var10001 = this.scanner;
								if (this.aktToken == 3) {
									var4 = var5 * this.scanner.ZahlGeben();
									this.aktToken = this.scanner.NächstesToken();
								} else {
									this.fehler.FehlerEintragen(R.getResources().getString("parse_error_expected_number"), this.scanner.PositionGeben());
								}
							}

						} else {
							var3 = 0;
							var10001 = this.scanner;
							if (this.aktToken != 9) {
								var10001 = this.scanner;
								if (this.aktToken == 2) {
									var3 = 1;
									label = this.scanner.BezeichnerGeben();
									if (this.marken.containsKey(label)) {
										var4 = (Integer)this.marken.get(label);
									} else {
										this.fixierungen.put(this.pc + 1, label);
									}

									this.aktToken = this.scanner.NächstesToken();
								} else {
									var10001 = this.scanner;
									if (this.aktToken == 3) {
										var3 = 1;
										var4 = this.scanner.ZahlGeben();
										this.aktToken = this.scanner.NächstesToken();
										if (this.erweitert) {
											var10001 = this.scanner;
											if (this.aktToken == 9) {
												var3 = 4;
												this.aktToken = this.scanner.NächstesToken();
												var10001 = this.scanner;
												if (this.aktToken == 2) {
													if (!this.scanner.BezeichnerGeben().equals("SP") && !this.scanner.BezeichnerGeben().equals("sp")) {
														this.fehler.FehlerEintragen("'SP' " + R.getResources().getString("parse_error_x_expected"), this.scanner.PositionGeben());
													}

													this.aktToken = this.scanner.NächstesToken();
												} else {
													var10001 = this.scanner;
													if (this.aktToken == 3) {
														this.fehler.FehlerEintragen("'SP' " + R.getResources().getString("parse_error_x_expected"), this.scanner.PositionGeben());
														this.aktToken = this.scanner.NächstesToken();
													} else {
														this.fehler.FehlerEintragen("'SP' " + R.getResources().getString("parse_error_x_expected"), this.scanner.PositionGeben());
													}
												}

												var10001 = this.scanner;
												if (this.aktToken == 10) {
													this.aktToken = this.scanner.NächstesToken();
												} else {
													this.fehler.FehlerEintragen("')' " + R.getResources().getString("parse_error_x_expected"), this.scanner.PositionGeben());
												}
											}
										}
									} else {
										var10001 = this.scanner;
										if (this.aktToken == 8) {
											var3 = 2;
											this.aktToken = this.scanner.NächstesToken();
											var5 = 1;
											var10001 = this.scanner;
											if (this.aktToken == 2) {
												label = this.scanner.BezeichnerGeben();
												if (this.marken.containsKey(label)) {
													var4 = (Integer)this.marken.get(label);
												} else {
													this.fixierungen.put(this.pc + 1, label);
												}

												this.aktToken = this.scanner.NächstesToken();
											} else {
												var10001 = this.scanner;
												if (this.aktToken == 7) {
													var5 = -1;
													this.aktToken = this.scanner.NächstesToken();
												} else {
													var10001 = this.scanner;
													if (this.aktToken == 6) {
														this.aktToken = this.scanner.NächstesToken();
													}
												}

												var10001 = this.scanner;
												if (this.aktToken == 3) {
													var4 = var5 * this.scanner.ZahlGeben();
													this.aktToken = this.scanner.NächstesToken();
													if (this.erweitert) {
														var10001 = this.scanner;
														if (this.aktToken == 9) {
															var3 = 6;
															this.aktToken = this.scanner.NächstesToken();
															var10001 = this.scanner;
															if (this.aktToken == 2) {
																if (!this.scanner.BezeichnerGeben().equals("SP") && !this.scanner.BezeichnerGeben().equals("sp")) {
																	this.fehler.FehlerEintragen("'SP' " + R.getResources().getString("parse_error_x_expected"), this.scanner.PositionGeben());
																}

																this.aktToken = this.scanner.NächstesToken();
															} else {
																this.fehler.FehlerEintragen("'SP' " + R.getResources().getString("parse_error_x_expected"), this.scanner.PositionGeben());
															}

															var10001 = this.scanner;
															if (this.aktToken == 10) {
																this.aktToken = this.scanner.NächstesToken();
															} else {
																this.fehler.FehlerEintragen("')' " + R.getResources().getString("parse_error_x_expected"), this.scanner.PositionGeben());
															}
														}
													}
												} else {
													this.fehler.FehlerEintragen(R.getResources().getString("parse_error_expected_number"), this.scanner.PositionGeben());
												}
											}
										} else if (this.erweitert) {
											var10001 = this.scanner;
											if (this.aktToken == 11) {
												var3 = 5;
												this.aktToken = this.scanner.NächstesToken();
												var10001 = this.scanner;
												if (this.aktToken == 3) {
													var4 = this.scanner.ZahlGeben();
													this.aktToken = this.scanner.NächstesToken();
												} else {
													var10001 = this.scanner;
													if (this.aktToken == 2) {
														this.fehler.FehlerEintragen(R.getResources().getString("parse_error_expected_number"), this.scanner.PositionGeben());
														this.aktToken = this.scanner.NächstesToken();
													}
												}

												var10001 = this.scanner;
												if (this.aktToken == 9) {
													this.aktToken = this.scanner.NächstesToken();
													var10001 = this.scanner;
													if (this.aktToken == 2) {
														if (!this.scanner.BezeichnerGeben().equals("SP") && !this.scanner.BezeichnerGeben().equals("sp")) {
															this.fehler.FehlerEintragen("'SP' " + R.getResources().getString("parse_error_x_expected"), this.scanner.PositionGeben());
														}

														this.aktToken = this.scanner.NächstesToken();
													} else {
														var10001 = this.scanner;
														if (this.aktToken == 3) {
															this.fehler.FehlerEintragen("'SP' " + R.getResources().getString("parse_error_x_expected"), this.scanner.PositionGeben());
															this.aktToken = this.scanner.NächstesToken();
														} else {
															this.fehler.FehlerEintragen("'SP' " + R.getResources().getString("parse_error_x_expected"), this.scanner.PositionGeben());
														}
													}

													var10001 = this.scanner;
													if (this.aktToken == 10) {
														this.aktToken = this.scanner.NächstesToken();
													} else {
														this.fehler.FehlerEintragen("')' " + R.getResources().getString("parse_error_x_expected"), this.scanner.PositionGeben());
													}
												} else {
													this.fehler.FehlerEintragen("(", this.scanner.PositionGeben());
												}
											}
										}
									}
								}
							} else {
								this.aktToken = this.scanner.NächstesToken();
								var3 = 3;
								var10001 = this.scanner;
								if (this.aktToken == 2) {
									label = this.scanner.BezeichnerGeben();
									if (this.erweitert && (label.equals("SP") || label.equals("sp"))) {
										var3 = 4;
										var4 = 0;
									} else if (this.marken.containsKey(label)) {
										var4 = (Integer)this.marken.get(label);
									} else {
										this.fixierungen.put(this.pc + 1, label);
									}

									this.aktToken = this.scanner.NächstesToken();
								} else {
									var10001 = this.scanner;
									if (this.aktToken == 3) {
										var4 = this.scanner.ZahlGeben();
										this.aktToken = this.scanner.NächstesToken();
									} else {
										this.fehler.FehlerEintragen(R.getResources().getString("parse_error_expected_number_or_label"), this.scanner.PositionGeben());
									}
								}

								var10001 = this.scanner;
								if (this.aktToken == 10) {
									this.aktToken = this.scanner.NächstesToken();
								} else {
									this.fehler.FehlerEintragen("')' " + R.getResources().getString("parse_error_x_expected"), this.scanner.PositionGeben());
								}
							}

						}
						this.speicher.WortSetzen(this.pc, var3 * 256 + var2);
						++this.pc;
						this.speicher.WortSetzen(this.pc, var4);
						++this.pc;
					}

					switch(var2) {
						case 1:
						case 99:
							if (var3 != 0) {
								this.fehler.FehlerEintragen(R.getResources().getString("parse_error_invalid_address"), this.scanner.PositionGeben());
							}
						case 2:
						case 3:
						case 4:
						case 9:
						case 14:
						case 16:
						case 17:
						case 18:
						case 19:
						case 22:
						case 23:
						case 24:
						case 27:
						case 28:
						case 29:
						case 37:
						case 38:
						case 39:
						case 40:
						case 41:
						case 42:
						case 43:
						case 44:
						case 45:
						case 46:
						case 47:
						case 48:
						case 49:
						case 50:
						case 51:
						case 52:
						case 53:
						case 54:
						case 55:
						case 56:
						case 57:
						case 58:
						case 59:
						case 60:
						case 61:
						case 62:
						case 63:
						case 64:
						case 65:
						case 66:
						case 67:
						case 68:
						case 69:
						case 70:
						case 71:
						case 72:
						case 73:
						case 74:
						case 75:
						case 76:
						case 77:
						case 78:
						case 79:
						case 80:
						case 81:
						case 82:
						case 83:
						case 84:
						case 85:
						case 86:
						case 87:
						case 88:
						case 89:
						case 90:
						case 91:
						case 92:
						case 93:
						case 94:
						case 95:
						case 96:
						case 97:
						case 98:
						default:
							break;
						case 5:
							if (var3 == 0) {
								this.fehler.FehlerEintragen(R.getResources().getString("parse_error_missing_address"), this.scanner.PositionGeben());
							} else if (var3 == 2) {
								this.fehler.FehlerEintragen(R.getResources().getString("parse_error_invalid_address_mode"), this.scanner.PositionGeben());
							}

							if (!this.erweitert) {
								this.fehler.FehlerEintragen(R.getResources().getString("parse_error_extended_instruction"), this.scanner.PositionGeben());
							}
							break;
						case 6:
						case 25:
						case 26:
							if (var3 != 0) {
								this.fehler.FehlerEintragen(R.getResources().getString("parse_error_invalid_address"), this.scanner.PositionGeben());
							}

							if (!this.erweitert) {
								this.fehler.FehlerEintragen(R.getResources().getString("parse_error_extended_instruction"), this.scanner.PositionGeben());
							}
							break;
						case 7:
						case 8:
							if (var3 != 2) {
								this.fehler.FehlerEintragen(R.getResources().getString("parse_error_invalid_address"), this.scanner.PositionGeben());
							}
							break;
						case 10:
						case 11:
						case 12:
						case 13:
						case 15:
						case 20:
							if (var3 == 0) {
								this.fehler.FehlerEintragen(R.getResources().getString("parse_error_missing_address"), this.scanner.PositionGeben());
							}
							break;
						case 21:
						case 30:
						case 31:
						case 32:
						case 33:
						case 34:
						case 35:
						case 36:
							if (var3 == 0) {
								this.fehler.FehlerEintragen(R.getResources().getString("parse_error_missing_address"), this.scanner.PositionGeben());
							} else if (var3 == 2) {
								this.fehler.FehlerEintragen(R.getResources().getString("parse_error_invalid_address_mode"), this.scanner.PositionGeben());
							}
					}

					if (this.aktToken != 0 && this.aktToken != 5) {
						this.fehler.FehlerEintragen(R.getResources().getString("parse_error_unnecessary_address"), this.scanner.PositionGeben());
					}
				}
			}
		}

		if (this.pc > 65536) {
			this.fehler.FehlerEintragen("Programm zu lang", this.scanner.PositionGeben());
		}

		Iterator var7 = this.fixierungen.entrySet().iterator();

		while(var7.hasNext()) {
			Entry var8 = (Entry)var7.next();
			if (this.marken.containsKey(var8.getValue())) {
				this.speicher.WortSetzen((Integer)var8.getKey(), (Integer)this.marken.get(var8.getValue()));
			} else {
				this.fehler.FehlerEintragen(R.getResources().getString("parse_error_label_not_defined") + ": " + (String)var8.getValue(), this.scanner.PositionGeben());
			}
		}

		if (this.fehler.FehlerAufgetreten()) {
			if (this.pc > 65536) {
				this.pc = 65534;
				this.speicher.WortSetzen(65534, -1);
				this.speicher.WortSetzen(65535, -1);
			}

			for(int var9 = 0; var9 < this.pc; ++var9) {
				this.speicher.WortSetzen(var9, 0);
			}
		}

	}

	private void Überspringen() {
		while(this.aktToken != 0 && this.aktToken != 5) {
			this.aktToken = this.scanner.NächstesToken();
		}

	}
}
