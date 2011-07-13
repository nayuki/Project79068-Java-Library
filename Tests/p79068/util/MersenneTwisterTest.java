package p79068.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import p79068.random.MersenneTwister;
import p79068.random.Random;


public final class MersenneTwisterTest {
	
	private static long[] output = {
		1067595299L,  955945823L,  477289528L, 4107218783L, 4228976476L, 3344332714L, 3355579695L,  227628506L,  810200273L, 2591290167L,
		2560260675L, 3242736208L,  646746669L, 1479517882L, 4245472273L, 1143372638L, 3863670494L, 3221021970L, 1773610557L, 1138697238L,
		1421897700L, 1269916527L, 2859934041L, 1764463362L, 3874892047L, 3965319921L,   72549643L, 2383988930L, 2600218693L, 3237492380L,
		2792901476L,  725331109L,  605841842L,  271258942L,  715137098L, 3297999536L, 1322965544L, 4229579109L, 1395091102L, 3735697720L,
		2101727825L, 3730287744L, 2950434330L, 1661921839L, 2895579582L, 2370511479L, 1004092106L, 2247096681L, 2111242379L, 3237345263L,
		4082424759L,  219785033L, 2454039889L, 3709582971L,  835606218L, 2411949883L, 2735205030L,  756421180L, 2175209704L, 1873865952L,
		2762534237L, 4161807854L, 3351099340L,  181129879L, 3269891896L,  776029799L, 2218161979L, 3001745796L, 1866825872L, 2133627728L,
		  34862734L, 1191934573L, 3102311354L, 2916517763L, 1012402762L, 2184831317L, 4257399449L, 2899497138L, 3818095062L, 3030756734L,
		1282161629L,  420003642L, 2326421477L, 2741455717L, 1278020671L, 3744179621L,  271777016L, 2626330018L, 2560563991L, 3055977700L,
		4233527566L, 1228397661L, 3595579322L, 1077915006L, 2395931898L, 1851927286L, 3013683506L, 1999971931L, 3006888962L, 1049781534L,
		1488758959L, 3491776230L,  104418065L, 2448267297L, 3075614115L, 3872332600L,  891912190L, 3936547759L, 2269180963L, 2633455084L,
		1047636807L, 2604612377L, 2709305729L, 1952216715L,  207593580L, 2849898034L,  670771757L, 2210471108L,  467711165L,  263046873L,
		3569667915L, 1042291111L, 3863517079L, 1464270005L, 2758321352L, 3790799816L, 2301278724L, 3106281430L,    7974801L, 2792461636L,
		 555991332L,  621766759L, 1322453093L,  853629228L,  686962251L, 1455120532L,  957753161L, 1802033300L, 1021534190L, 3486047311L,
		1902128914L, 3701138056L, 4176424663L, 1795608698L,  560858864L, 3737752754L, 3141170998L, 1553553385L, 3367807274L,  711546358L,
		2475125503L,  262969859L,  251416325L, 2980076994L, 1806565895L,  969527843L, 3529327173L, 2736343040L, 2987196734L, 1649016367L,
		2206175811L, 3048174801L, 3662503553L, 3138851612L, 2660143804L, 1663017612L, 1816683231L,  411916003L, 3887461314L, 2347044079L,
		1015311755L, 1203592432L, 2170947766L, 2569420716L,  813872093L, 1105387678L, 1431142475L,  220570551L, 4243632715L, 4179591855L,
		2607469131L, 3090613241L,  282341803L, 1734241730L, 1391822177L, 1001254810L,  827927915L, 1886687171L, 3935097347L, 2631788714L,
		3905163266L,  110554195L, 2447955646L, 3717202975L, 3304793075L, 3739614479L, 3059127468L,  953919171L, 2590123714L, 1132511021L,
		3795593679L, 2788030429L,  982155079L, 3472349556L,  859942552L, 2681007391L, 2299624053L,  647443547L,  233600422L,  608168955L,
		3689327453L, 1849778220L, 1608438222L, 3968158357L, 2692977776L, 2851872572L,  246750393L, 3582818628L, 3329652309L, 4036366910L,
		1012970930L,  950780808L, 3959768744L, 2538550045L,  191422718L, 2658142375L, 3276369011L, 2927737484L, 1234200027L, 1920815603L,
		3536074689L, 1535612501L, 2184142071L, 3276955054L,  428488088L, 2378411984L, 4059769550L, 3913744741L, 2732139246L,   64369859L,
		3755670074L,  842839565L, 2819894466L, 2414718973L, 1010060670L, 1839715346L, 2410311136L,  152774329L, 3485009480L, 4102101512L,
		2852724304L,  879944024L, 1785007662L, 2748284463L, 1354768064L, 3267784736L, 2269127717L, 3001240761L, 3179796763L,  895723219L,
		 865924942L, 4291570937L,   89355264L, 1471026971L, 4114180745L, 3201939751L, 2867476999L, 2460866060L, 3603874571L, 2238880432L,
		3308416168L, 2072246611L, 2755653839L, 3773737248L, 1709066580L, 4282731467L, 2746170170L, 2832568330L,  433439009L, 3175778732L,
		  26248366L, 2551382801L,  183214346L, 3893339516L, 1928168445L, 1337157619L, 3429096554L, 3275170900L, 1782047316L, 4264403756L,
		1876594403L, 4289659572L, 3223834894L, 1728705513L, 4068244734L, 2867840287L, 1147798696L,  302879820L, 1730407747L, 1923824407L,
		1180597908L, 1569786639L,  198796327L,  560793173L, 2107345620L, 2705990316L, 3448772106L, 3678374155L,  758635715L,  884524671L,
		 486356516L, 1774865603L, 3881226226L, 2635213607L, 1181121587L, 1508809820L, 3178988241L, 1594193633L, 1235154121L,  326117244L,
		2304031425L,  937054774L, 2687415945L, 3192389340L, 2003740439L, 1823766188L, 2759543402L,   10067710L, 1533252662L, 4132494984L,
		  82378136L,  420615890L, 3467563163L,  541562091L, 3535949864L, 2277319197L, 3330822853L, 3215654174L, 4113831979L, 4204996991L,
		2162248333L, 3255093522L, 2219088909L, 2978279037L,  255818579L, 2859348628L, 3097280311L, 2569721123L, 1861951120L, 2907080079L,
		2719467166L,  998319094L, 2521935127L, 2404125338L,  259456032L, 2086860995L, 1839848496L, 1893547357L, 2527997525L, 1489393124L,
		2860855349L,   76448234L, 2264934035L,  744914583L, 2586791259L, 1385380501L,   66529922L, 1819103258L, 1899300332L, 2098173828L,
		1793831094L,  276463159L,  360132945L, 4178212058L,  595015228L,  177071838L, 2800080290L, 1573557746L, 1548998935L,  378454223L,
		1460534296L, 1116274283L, 3112385063L, 3709761796L,  827999348L, 3580042847L, 1913901014L,  614021289L, 4278528023L, 1905177404L,
		  45407939L, 3298183234L, 1184848810L, 3644926330L, 3923635459L, 1627046213L, 3677876759L,  969772772L, 1160524753L, 1522441192L,
		 452369933L, 1527502551L,  832490847L, 1003299676L, 1071381111L, 2891255476L,  973747308L, 4086897108L, 1847554542L, 3895651598L,
		2227820339L, 1621250941L, 2881344691L, 3583565821L, 3510404498L,  849362119L,  862871471L,  797858058L, 2867774932L, 2821282612L,
		3272403146L, 3997979905L,  209178708L, 1805135652L,    6783381L, 2823361423L,  792580494L, 4263749770L,  776439581L, 3798193823L,
		2853444094L, 2729507474L, 1071873341L, 1329010206L, 1289336450L, 3327680758L, 2011491779L,   80157208L,  922428856L, 1158943220L,
		1667230961L, 2461022820L, 2608845159L,  387516115L, 3345351910L, 1495629111L, 4098154157L, 3156649613L, 3525698599L, 4134908037L,
		 446713264L, 2137537399L, 3617403512L,  813966752L, 1157943946L, 3734692965L, 1680301658L, 3180398473L, 3509854711L, 2228114612L,
		1008102291L,  486805123L,  863791847L, 3189125290L, 1050308116L, 3777341526L, 4291726501L,  844061465L, 1347461791L, 2826481581L,
		 745465012L, 2055805750L, 4260209475L, 2386693097L, 2980646741L,  447229436L, 2077782664L, 1232942813L, 4023002732L, 1399011509L,
		3140569849L, 2579909222L, 3794857471L,  900758066L, 2887199683L, 1720257997L, 3367494931L, 2668921229L,  955539029L, 3818726432L,
		1105704962L, 3889207255L, 2277369307L, 2746484505L, 1761846513L, 2413916784L, 2685127085L, 4240257943L, 1166726899L, 4215215715L,
		3082092067L, 3960461946L, 1663304043L, 2087473241L, 4162589986L, 2507310778L, 1579665506L,  767234210L,  970676017L,  492207530L,
		1441679602L, 1314785090L, 3262202570L, 3417091742L, 1561989210L, 3011406780L, 1146609202L, 3262321040L, 1374872171L, 1634688712L,
		1280458888L, 2230023982L,  419323804L, 3262899800L,   39783310L, 1641619040L, 1700368658L, 2207946628L, 2571300939L, 2424079766L,
		 780290914L, 2715195096L, 3390957695L,  163151474L, 2309534542L, 1860018424L,  555755123L,  280320104L, 1604831083L, 2713022383L,
		1728987441L, 3639955502L,  623065489L, 3828630947L, 4275479050L, 3516347383L, 2343951195L, 2430677756L,  635534992L, 3868699749L,
		 808442435L, 3070644069L, 4282166003L, 2093181383L, 2023555632L, 1568662086L, 3422372620L, 4134522350L, 3016979543L, 3259320234L,
		2888030729L, 3185253876L, 4258779643L, 1267304371L, 1022517473L,  815943045L,  929020012L, 2995251018L, 3371283296L, 3608029049L,
		2018485115L,  122123397L, 2810669150L, 1411365618L, 1238391329L, 1186786476L, 3155969091L, 2242941310L, 1765554882L,  279121160L,
		4279838515L, 1641578514L, 3796324015L,   13351065L,  103516986L, 1609694427L,  551411743L, 2493771609L, 1316337047L, 3932650856L,
		4189700203L,  463397996L, 2937735066L, 1855616529L, 2626847990L,   55091862L, 3823351211L,  753448970L, 4045045500L, 1274127772L,
		1124182256L,   92039808L, 2126345552L,  425973257L,  386287896L, 2589870191L, 1987762798L, 4084826973L, 2172456685L, 3366583455L,
		3602966653L, 2378803535L, 2901764433L, 3716929006L, 3710159000L, 2653449155L, 3469742630L, 3096444476L, 3932564653L, 2595257433L,
		 318974657L, 3146202484L,  853571438L,  144400272L, 3768408841L,  782634401L, 2161109003L,  570039522L, 1886241521L,   14249488L,
		2230804228L, 1604941699L, 3928713335L, 3921942509L, 2155806892L,  134366254L,  430507376L, 1924011722L,  276713377L,  196481886L,
		3614810992L, 1610021185L, 1785757066L,  851346168L, 3761148643L, 2918835642L, 3364422385L, 3012284466L, 3735958851L, 2643153892L,
		3778608231L, 1164289832L,  205853021L, 2876112231L, 3503398282L, 3078397001L, 3472037921L, 1748894853L, 2740861475L,  316056182L,
		1660426908L,  168885906L,  956005527L, 3984354789L,  566521563L, 1001109523L, 1216710575L, 2952284757L, 3834433081L, 3842608301L,
		2467352408L, 3974441264L, 3256601745L, 1409353924L, 1329904859L, 2307560293L, 3125217879L, 3622920184L, 3832785684L, 3882365951L,
		2308537115L, 2659155028L, 1450441945L, 3532257603L, 3186324194L, 1225603425L, 1124246549L,  175808705L, 3009142319L, 2796710159L,
		3651990107L,  160762750L, 1902254979L, 1698648476L, 1134980669L,  497144426L, 3302689335L, 4057485630L, 3603530763L, 4087252587L,
		 427812652L,  286876201L,  823134128L, 1627554964L, 3745564327L, 2589226092L, 4202024494L,   62878473L, 3275585894L, 3987124064L,
		2791777159L, 1916869511L, 2585861905L, 1375038919L, 1403421920L,   60249114L, 3811870450L, 3021498009L, 2612993202L,  528933105L,
		2757361321L, 3341402964L, 2621861700L,  273128190L, 4015252178L, 3094781002L, 1621621288L, 2337611177L, 1796718448L, 1258965619L,
		4241913140L, 2138560392L, 3022190223L, 4174180924L,  450094611L, 3274724580L,  617150026L, 2704660665L, 1469700689L, 1341616587L,
		 356715071L, 1188789960L, 2278869135L, 1766569160L, 2795896635L,   57824704L, 2893496380L, 1235723989L, 1630694347L, 3927960522L,
		 428891364L, 1814070806L, 2287999787L, 4125941184L, 3968103889L, 3548724050L, 1025597707L, 1404281500L, 2002212197L,   92429143L,
		2313943944L, 2403086080L, 3006180634L, 3561981764L, 1671860914L, 1768520622L, 1803542985L,  844848113L, 3006139921L, 1410888995L,
		1157749833L, 2125704913L, 1789979528L, 1799263423L,  741157179L, 2405862309L,  767040434L, 2655241390L, 3663420179L, 2172009096L,
		2511931187L, 1680542666L,  231857466L, 1154981000L,  157168255L, 1454112128L, 3505872099L, 1929775046L, 2309422350L, 2143329496L,
		2960716902L,  407610648L, 2938108129L, 2581749599L,  538837155L, 2342628867L,  430543915L,  740188568L, 1937713272L, 3315215132L,
		2085587024L, 4030765687L,  766054429L, 3517641839L,  689721775L, 1294158986L, 1753287754L, 4202601348L, 1974852792L,   33459103L,
		3568087535L, 3144677435L, 1686130825L, 4134943013L, 3005738435L, 3599293386L,  426570142L,  754104406L, 3660892564L, 1964545167L,
		 829466833L,  821587464L, 1746693036L, 1006492428L, 1595312919L, 1256599985L, 1024482560L, 1897312280L, 2902903201L,  691790057L,
		1037515867L, 3176831208L, 1968401055L, 2173506824L, 1089055278L, 1748401123L, 2941380082L,  968412354L, 1818753861L, 2973200866L,
		3875951774L, 1119354008L, 3988604139L, 1647155589L, 2232450826L, 3486058011L, 3655784043L, 3759258462L,  847163678L, 1082052057L,
		 989516446L, 2871541755L, 3196311070L, 3929963078L,  658187585L, 3664944641L, 2175149170L, 2203709147L, 2756014689L, 2456473919L,
		3890267390L, 1293787864L, 2830347984L, 3059280931L, 4158802520L, 1561677400L, 2586570938L,  783570352L, 1355506163L,   31495586L,
		3789437343L, 3340549429L, 2092501630L,  896419368L,  671715824L, 3530450081L, 3603554138L, 1055991716L, 3442308219L, 1499434728L,
		3130288473L, 3639507000L,   17769680L, 2259741420L,  487032199L, 4227143402L, 3693771256L, 1880482820L, 3924810796L,  381462353L,
		4017855991L, 2452034943L, 2736680833L, 2209866385L, 2128986379L,  437874044L,  595759426L,  641721026L, 1636065708L, 3899136933L,
		 629879088L, 3591174506L,  351984326L, 2638783544L, 2348444281L, 2341604660L, 2123933692L,  143443325L, 1525942256L,  364660499L,
		 599149312L,  939093251L, 1523003209L,  106601097L,  376589484L, 1346282236L, 1297387043L,  764598052L, 3741218111L,  933457002L,
		1886424424L, 3219631016L,  525405256L, 3014235619L,  323149677L, 2038881721L, 4100129043L, 2851715101L, 2984028078L, 1888574695L,
		2014194741L, 3515193880L, 4180573530L, 3461824363L, 2641995497L, 3179230245L, 2902294983L, 2217320456L, 4040852155L, 1784656905L,
		3311906931L,   87498458L, 2752971818L, 2635474297L, 2831215366L, 3682231106L, 2920043893L, 3772929704L, 2816374944L,  309949752L,
		2383758854L,  154870719L,  385111597L, 1191604312L, 1840700563L,  872191186L, 2925548701L, 1310412747L, 2102066999L, 1504727249L,
		3574298750L, 1191230036L, 3330575266L, 3180292097L, 3539347721L,  681369118L, 3305125752L, 3648233597L,  950049240L, 4173257693L,
		1760124957L,  512151405L,  681175196L,  580563018L, 1169662867L, 4015033554L, 2687781101L,  699691603L, 2673494188L, 1137221356L,
		 123599888L,  472658308L, 1053598179L, 1012713758L, 3481064843L, 3759461013L, 3981457956L, 3830587662L, 1877191791L, 3650996736L,
		 988064871L, 3515461600L, 4089077232L, 2225147448L, 1249609188L, 2643151863L, 3896204135L, 2416995901L, 1397735321L, 3460025646L
	};
	
	
	
	@Test
	public void testMersenneTwister() {
		Random r = new MersenneTwister(new int[]{0x123, 0x234, 0x345, 0x456});
		for (int i = 0; i < output.length; i++)
			assertEquals(r.uniformInt(), (int)output[i]);
	}
	
}
