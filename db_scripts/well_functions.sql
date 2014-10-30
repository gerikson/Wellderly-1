
drop library wellderly_extensions cascade 
GO
CREATE LIBRARY wellderly_extensions AS '/localdisk/java/wellderly_extensions/extensions.jar' language 'Java'
GO
CREATE FUNCTION ReferenceRules AS LANGUAGE 'Java' NAME 'edu.sdsc.extensions.ReferenceRulesFactory' LIBRARY wellderly_extensions;
GO
CREATE FUNCTION AltRules AS LANGUAGE 'Java' NAME 'edu.sdsc.extensions.AltRulesFactory' LIBRARY wellderly_extensions;
GO
CREATE FUNCTION PosRules AS LANGUAGE 'Java' NAME 'edu.sdsc.extensions.PosRulesFactory' LIBRARY wellderly_extensions;
GO
CREATE FUNCTION TypeRules AS LANGUAGE 'Java' NAME 'edu.sdsc.extensions.TypeRulesFactory' LIBRARY wellderly_extensions;
GO




